package ua.polohalo.zoomabletextureview.sample;

import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import ua.polohalo.zoomabletextureview.ZoomableTextureView;

public class ExoPlayerViewActivity extends AppCompatActivity {

    SimpleExoPlayer player;
    MediaSource videoSource;
    String path = "http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4";
    ZoomableExoPlayerView exoPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayerview);
        exoPlayerView = findViewById(R.id.exo_player_view);

        //1. creating an ExoPlayer with default parameters from Getting Started guide(https://google.github.io/ExoPlayer/guide.html)
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        DataSource.Factory mediaDataSourceFactory = new DefaultDataSourceFactory(this, bandwidthMeter,
                new DefaultHttpDataSourceFactory(Util.getUserAgent(this, getResources().getString(R.string.app_name)), bandwidthMeter));
        //2. prepare video source from url
        videoSource = new ExtractorMediaSource(Uri.parse(path), mediaDataSourceFactory,
                new DefaultExtractorsFactory(), new Handler(), null);

        //2. create the player
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, new DefaultLoadControl());
        exoPlayerView.setControllerShowTimeoutMs(0);
        exoPlayerView.setPlayer(player);
        player.setPlayWhenReady(true);
        player.prepare(videoSource);

    }
}
