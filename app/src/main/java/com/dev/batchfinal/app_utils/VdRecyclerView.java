/*
package com.dev.batchfinal.app_utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.RequestManager;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class VdRecyclerView extends RecyclerView {
    private static final String TAG = "VdRecyclerView";
    private static final String AppName = "Batch";
    //Minimum Video you want to buffer while Playing
    private int MIN_BUFFER_DURATION = 2000;
    //Max Video you want to buffer during PlayBack
    private int MAX_BUFFER_DURATION = 5000;
    //Min Video you want to buffer before start Playing it
    private int MIN_PLAYBACK_START_BUFFER = 1500;
    //Min video You want to buffer when user resumes video
    private int MIN_PLAYBACK_RESUME_BUFFER = 2000;
    String youTubeUrl = "";
    boolean fullscreen = false;
    private Activity mActivity;
    private FrameLayout playerViewLayout;
    private ImageView mediaCoverImage, volumeControl, btnFullScreen;
    private ProgressBar progressBar;
    private View viewHolderParent;
    private FrameLayout mediaContainer;
    private PlayerView videoSurfaceView;
    private SimpleExoPlayer videoPlayer;
    private Dialog mFullScreenDialog;
    */
/**
     * variable declaration
     *//*

    // Media List
    private List<Datum> mediaObjects = new ArrayList<>();
    private int videoSurfaceDefaultHeight = 0;
    private int screenDefaultHeight = 0;
    private Context context;
    private int playPosition = -1;
    private boolean isVideoViewAdded,mExoPlayerFullscreen=false;
    private boolean isPlayerPlaying;
    private RequestManager requestManager;
    // controlling volume state
    private VolumeState volumeState;
    private OnClickListener videoViewClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            toggleVolume();
        }
    };
    public VdRecyclerView(@NonNull Context context) {
        super(context);
        init(context);

    }
    public VdRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }
    private void init(Context context) {
        this.context = context.getApplicationContext();
        Display display = ((WindowManager) Objects.requireNonNull(
                getContext().getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        videoSurfaceDefaultHeight = point.x;
        screenDefaultHeight = point.y;
        videoSurfaceView = new PlayerView(this.context);
        videoSurfaceView.showController();
//        View view = LayoutInflater.from(context).inflate(R.layout.player_view_exo, null, false);
//        videoSurfaceView = (PlayerView) view.getRootView();
        //videoSurfaceView = (PlayerView) findViewById(R.id.video_player_view);
        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        LoadControl loadControl = new DefaultLoadControl.Builder()
                .setAllocator(new DefaultAllocator(true, 16))
                .setBufferDurationsMs(MIN_BUFFER_DURATION,
                        MAX_BUFFER_DURATION,
                        MIN_PLAYBACK_START_BUFFER,
                        MIN_PLAYBACK_RESUME_BUFFER)
                .setTargetBufferBytes(-1)
                .setPrioritizeTimeOverSizeThresholds(true).createDefaultLoadControl();

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        videoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
        // Disable Player Control
        videoPlayer.setRepeatMode(Player.REPEAT_MODE_OFF);
        videoSurfaceView.setUseController(true);
        // Bind the player to the view.
        videoSurfaceView.setPlayer(videoPlayer);

        // Turn on Volume
        setVolumeControl(VolumeState.OFF);
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mediaCoverImage != null) {
                        // show the old thumbnail
                        mediaCoverImage.setVisibility(GONE);
                    }
                    // There's a special case when the end of the list has been reached.
                    // Need to handle that with this bit of logic
                    if (!recyclerView.canScrollVertically(1)) {
                        playVideo();
                        playVideo(true);

                    } else {
                        pauseVideo();
                        playVideo(false);

                    }
                }
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        addOnChildAttachStateChangeListener(new OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
            }
            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                if (viewHolderParent != null && viewHolderParent.equals(view)) {
                    resetVideoView();
                }
            }
        });
        videoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
            }
            @Override
            public void onTracksChanged(TrackGroupArray trackGroups,
                                        TrackSelectionArray trackSelections) {
            }
            @Override
            public void onLoadingChanged(boolean isLoading) {
            }
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_BUFFERING:
                        Log.e(TAG, "onPlayerStateChanged: Buffering video.");
                        if (progressBar != null) {
                            progressBar.setVisibility(VISIBLE);
                            volumeControl.setVisibility(GONE);
                            btnFullScreen.setVisibility(GONE);
                        }
                        break;
                    case Player.STATE_ENDED:
                        Log.d(TAG, "onPlayerStateChanged: Video ended.");
                        videoPlayer.seekTo(0);
                        videoPlayer.setPlayWhenReady(false);
                        //resetVideoView();
                        break;
                    case Player.STATE_IDLE:
                        break;
                    case Player.STATE_READY:
                        Log.e(TAG, "onPlayerStateChanged: Ready to play.");
                        if (progressBar != null) {
                            progressBar.setVisibility(GONE);
                            volumeControl.setVisibility(VISIBLE);
                            btnFullScreen.setVisibility(VISIBLE);
                        }
                        if (!isVideoViewAdded) {
                            addVideoView();
                            toggleVolumeIcon();
                        }
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onRepeatModeChanged(int repeatMode) {
            }
            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            }
            @Override
            public void onPlayerError(ExoPlaybackException error) {
            }
            @Override
            public void onPositionDiscontinuity(int reason) {
            }
            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            }
            @Override
            public void onSeekProcessed() {
            }
        });
    }
    public void playVideo(boolean isEndOfList) {
        int targetPosition;
        if (!isEndOfList) {
            int startPosition = ((LinearLayoutManager) Objects.requireNonNull(
                    getLayoutManager())).findFirstVisibleItemPosition();
            int endPosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
            // if there is more than 2 list-items on the screen, set the difference to be 1
            if (endPosition - startPosition > 1) {
                endPosition = startPosition + 1;
            }
            // something is wrong. return.
            if (startPosition < 0 || endPosition < 0) {
                return;
            }
            // if there is more than 1 list-item on the screen
            if (startPosition != endPosition) {
                int startPositionVideoHeight = getVisibleVideoSurfaceHeight(startPosition);
                int endPositionVideoHeight = getVisibleVideoSurfaceHeight(endPosition);
                targetPosition =
                        startPositionVideoHeight > endPositionVideoHeight ? startPosition : endPosition;
            } else {
                targetPosition = startPosition;
            }
        } else {
            targetPosition = mediaObjects.size() - 1;
        }
        Log.d(TAG, "playVideo: target position: " + targetPosition);
        // video is already playing so return
        if (targetPosition == playPosition) {
            return;
        }
        // set the position of the list-item that is to be played
        playPosition = targetPosition;
        if (videoSurfaceView == null) {
            return;
        }
        // remove any old surface views from previously playing videos
        videoSurfaceView.setVisibility(INVISIBLE);
        removeVideoView(videoSurfaceView);
        int currentPosition =
                targetPosition - ((LinearLayoutManager) Objects.requireNonNull(
                        getLayoutManager())).findFirstVisibleItemPosition();
        View child = getChildAt(currentPosition);
        if (child == null) {
            return;
        }
        if(mediaObjects.size() > 0 && mediaObjects.get(targetPosition).getPostType().equalsIgnoreCase("video")){
            AdapterWallList.VideoViewHolder holder = (AdapterWallList.VideoViewHolder) child.getTag();
            if (holder == null) {
                playPosition = -1;
                return;
            }
            mActivity = holder.mactivity;
            playerViewLayout = holder.playerViewLayout;
            mediaCoverImage = holder.thumbnail;
            progressBar = holder.progressBar;
            volumeControl = holder.volumeControl;
            viewHolderParent = holder.itemView;
            requestManager = holder.requestManager;
            mediaContainer = holder.playerViewLayout;
            btnFullScreen = holder.btnFullscreen;
            //videoSurfaceView = holder.videoSurfaceView;
            videoSurfaceView.setPlayer(videoPlayer);
            //viewHolderParent.setOnClickListener(videoViewClickListener);
            volumeControl.setOnClickListener(videoViewClickListener);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    context, Util.getUserAgent(context, AppName));
            DefaultExtractorsFactory extractorsFactory =new DefaultExtractorsFactory();

            // Create a data source factory.
           // DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();


            String mediaUrl = mediaObjects.get(targetPosition).getMobileUrl();
            if (mediaUrl != null) {
                MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                        .setExtractorsFactory(extractorsFactory)
                        .createMediaSource(Uri.parse(mediaUrl));
                // Create a progressive media source pointing to a stream uri.
//                MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
//                        .createMediaSource(Uri.parse(mediaUrl));
//                MediaSource sampleSource =
//                        new ExtractorMediaSource(Uri.parse(mediaUrl), dataSourceFactory, new DefaultExtractorsFactory(),
//                                null, null);
                videoPlayer.prepare(videoSource);
                videoPlayer.seekTo(0);
                videoPlayer.setPlayWhenReady(true);
            }
            mFullScreenDialog = new Dialog(mActivity, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
                public void onBackPressed() {
                    if (mExoPlayerFullscreen) {    //mExoPlayerFullscreen is a boolean to check if it is already fullscreen or not
                        //closeFullscreenDialog();
                        ((ViewGroup) playerViewLayout.getParent()).removeView(playerViewLayout);
                        ((FrameLayout) viewHolderParent.findViewById(R.id.media_container_video)).addView(playerViewLayout);
                        mExoPlayerFullscreen = false;
                        mFullScreenDialog.dismiss();
                        btnFullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_24);
                    }
                    super.onBackPressed();
                }
            };
            btnFullScreen.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mExoPlayerFullscreen) {
                        //mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                        //openFullscreenDialog();
                        ((ViewGroup) playerViewLayout.getParent()).removeView(playerViewLayout);
                        mFullScreenDialog.addContentView(playerViewLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        btnFullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_exit_24);
                        mExoPlayerFullscreen = true;
                        mFullScreenDialog.show();
                    } else {
                        //mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                        //closeFullscreenDialog();
                        ((ViewGroup) playerViewLayout.getParent()).removeView(playerViewLayout);
                        ((FrameLayout) viewHolderParent.findViewById(R.id.media_container_video)).addView(playerViewLayout);
                        mExoPlayerFullscreen = false;
                        mFullScreenDialog.dismiss();

                        btnFullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_24);
                    }
                }
            });

//            btnFullScreen.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(fullscreen) {
//                        btnFullScreen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.exo_controls_fullscreen_exit));
//                        mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//                        if(mActivity.getActionBar() != null){
//                            mActivity.getActionBar().show();
//                        }
//                       // mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoSurfaceView.getLayoutParams();
//                        params.width = params.MATCH_PARENT;
//                        params.height = (int) ( 200 * context.getResources().getDisplayMetrics().density);
//                        videoSurfaceView.setLayoutParams(params);
//                        fullscreen = false;
//                    }else{
//                        btnFullScreen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.exo_controls_fullscreen_enter));
//                        mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
//                                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//                        if(mActivity.getActionBar() != null){
//                            mActivity.getActionBar().show();
//                        }
//                        //mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) videoSurfaceView.getLayoutParams();
//                        params.width = params.MATCH_PARENT;
//                        params.height = params.MATCH_PARENT;
//                        videoSurfaceView.setLayoutParams(params);
//                        fullscreen = true;
//                    }
//                }
//            });


        }



    }


    private int getVisibleVideoSurfaceHeight(int playPosition) {
        int at = playPosition - ((LinearLayoutManager) Objects.requireNonNull(
                getLayoutManager())).findFirstVisibleItemPosition();
        Log.d(TAG, "getVisibleVideoSurfaceHeight: at: " + at);
        View child = getChildAt(at);
        if (child == null) {
            return 0;
        }
        int[] location = new int[2];
        child.getLocationInWindow(location);
        if (location[1] < 0) {
            return location[1] + videoSurfaceDefaultHeight;
        } else {
            return screenDefaultHeight - location[1];
        }
    }
    // Remove the old player
    private void removeVideoView(PlayerView videoView) {
        ViewGroup parent = (ViewGroup) videoView.getParent();
        if (parent == null) {
            return;
        }
        int index = parent.indexOfChild(videoView);
        if (index >= 0) {
            parent.removeViewAt(index);
            isVideoViewAdded = false;
            viewHolderParent.setOnClickListener(null);
        }
    }
    private void addVideoView() {
        mediaContainer.addView(videoSurfaceView);
        isVideoViewAdded = true;
        videoSurfaceView.requestFocus();
        videoSurfaceView.setVisibility(VISIBLE);
        videoSurfaceView.setAlpha(1);
        mediaCoverImage.setVisibility(GONE);
    }
    private void resetVideoView() {
        if (isVideoViewAdded) {
            removeVideoView(videoSurfaceView);
            playPosition = -1;
            videoSurfaceView.setVisibility(INVISIBLE);
            mediaCoverImage.setVisibility(GONE);
        }
    }
    public void releasePlayer() {
        if (videoPlayer != null) {
            videoPlayer.release();
            videoPlayer = null;
        }
        viewHolderParent = null;
    }
    public void onPausePlayer() {
        if (videoPlayer != null) {
            videoPlayer.stop(true);
        }
    }
    private void toggleVolume() {
        if (videoPlayer != null) {
            if (volumeState == VolumeState.OFF) {
                Log.d(TAG, "togglePlaybackState: enabling volume.");
                setVolumeControl(VolumeState.ON);
            } else if (volumeState == VolumeState.ON) {
                Log.d(TAG, "togglePlaybackState: disabling volume.");
                setVolumeControl(VolumeState.OFF);
            }
        }
    }

    private void toggleVolumeIcon() {
        if (videoPlayer != null) {
            if (volumeState == VolumeState.OFF) {
                volumeControl.setVisibility(VISIBLE);
                requestManager.load(R.drawable.ic_baseline_volume_off_24)
                        .into(volumeControl);
            } else if (volumeState == VolumeState.ON) {
                volumeControl.setVisibility(VISIBLE);
                requestManager.load(R.drawable.ic_baseline_volume_up_24)
                        .into(volumeControl);
            }
        }
    }
    private void setVolumeControl(VolumeState state) {
        volumeState = state;
        if (state == VolumeState.OFF) {
            videoPlayer.setVolume(0f);
            animateVolumeControl();
        } else if (state == VolumeState.ON) {
            videoPlayer.setVolume(1f);
            animateVolumeControl();
        }
    }
    private void animateVolumeControl() {
        if (volumeControl != null) {
           // volumeControl.bringToFront();
            if (volumeState == VolumeState.OFF) {

                requestManager.load(R.drawable.ic_baseline_volume_off_24)
                        .into(volumeControl);
            } else if (volumeState == VolumeState.ON) {

                requestManager.load(R.drawable.ic_baseline_volume_up_24)
                        .into(volumeControl);
            }
//            volumeControl.animate().cancel();
//            volumeControl.setAlpha(1f);
//            volumeControl.animate()
//                    .alpha(0f)
//                    .setDuration(600).setStartDelay(1000);
        }
    }
    public void setMediaObjects(List<Datum> mediaObjects) {
        this.mediaObjects = mediaObjects;
    }
    */
/**
     * Volume ENUM
     *//*

    private enum VolumeState {
        ON, OFF
    }

    public void goToBackground() {
        if (videoPlayer != null) {
            setVolumeControl(VolumeState.OFF);
            isPlayerPlaying = videoPlayer.getPlayWhenReady();
            videoPlayer.setPlayWhenReady(false);
        }
    }

    public void goToForeground() {
        if (videoPlayer != null) {
            setVolumeControl(VolumeState.ON);
            videoPlayer.setPlayWhenReady(isPlayerPlaying);
        }
    }

    public void pauseVideo() {
        if (videoPlayer != null) {
            isPlayerPlaying = videoPlayer.getPlayWhenReady();
            videoPlayer.setPlayWhenReady(false);
        }
    }

    public void playVideo() {
        if (videoPlayer != null) {
            videoPlayer.setPlayWhenReady(true);
        }
    }

}*/
