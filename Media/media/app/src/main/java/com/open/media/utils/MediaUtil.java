package com.open.media.utils;

import android.content.res.AssetFileDescriptor;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.util.Log;

import com.open.media.MainActivity;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * MediaUtil used for Singleto
 * Created by vivian on 2017/6/27.
 */

public class MediaUtil {
    private static final String TAG = MainActivity.class.getSimpleName();


    private final static int ALLOCATE_BUFFER = 500 * 1024;

    private static class MediaUtilHolder {
        private static final MediaUtil INSTANCE = new MediaUtil();
    }

    private MediaUtil() {
    }

    public static final MediaUtil getInstance() {
        return MediaUtilHolder.INSTANCE;
    }

    /**
     * split/extract video or audio through MediaExtractor
     */
    public boolean extractMedia(AssetFileDescriptor source, String outPath, boolean isMedia) {
        MediaExtractor mediaExtractor = new MediaExtractor();
        MediaMuxer mediaMuxer = null;
        try {
            mediaMuxer = new MediaMuxer(outPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);

            // set data source
            mediaExtractor.setDataSource(source);

            // get video or audio 取出视频或音频的信号
            int mediaTrack = getTrack(mediaExtractor, isMedia);

            // prepare media information
            int writeTrackIndex = prepareMediaInfo(mediaExtractor, mediaMuxer, mediaTrack, isMedia);

            // 读取写入帧数据
            writeSampleData(mediaExtractor, mediaMuxer, writeTrackIndex, mediaTrack);

            return true;
        } catch (IOException e) {
            Log.w(TAG, "extractMedia ex", e);
        } finally {
            try {
                if (mediaMuxer != null) {
                    mediaMuxer.stop();
                    mediaMuxer.release();
                }
                if (mediaExtractor != null) {
                    mediaExtractor.release();
                }
            } catch (Exception e) {
                Log.w(TAG, "extractMedia release ex", e);
            }
        }

        return false;
    }

    public boolean combineMedia(String inputVideoPath, String inputAudioPath, String outPath) {
        MediaExtractor videoExtractor = new MediaExtractor();
        MediaExtractor audioExtractor = new MediaExtractor();
        MediaMuxer mediaMuxer = null;
        try {
            mediaMuxer = new MediaMuxer(outPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);

            // set data source
            videoExtractor.setDataSource(inputVideoPath);
            audioExtractor.setDataSource(inputAudioPath);

            // get video or audio 取出视频或音频的信号
            int videoTrack = getTrack(videoExtractor, true);
            int audioTrack = getTrack(audioExtractor, false);

            // change to video oraudio track 切换道视频或音频信号的信道
            videoExtractor.selectTrack(videoTrack);
            MediaFormat videoFormat = videoExtractor.getTrackFormat(videoTrack);
            audioExtractor.selectTrack(audioTrack);
            MediaFormat audioFormat = audioExtractor.getTrackFormat(audioTrack);
            //追踪此信道
            int writeVideoIndex = mediaMuxer.addTrack(videoFormat);
            int writeAudioIndex = mediaMuxer.addTrack(audioFormat);
            mediaMuxer.start();

            // 读取写入帧数据
            writeSampleData(videoExtractor, mediaMuxer, writeVideoIndex, videoTrack);
            writeSampleData(audioExtractor, mediaMuxer, writeAudioIndex, audioTrack);

            return true;
        } catch (IOException e) {
            Log.w(TAG, "combineMedia ex", e);
        } finally {
            try {
                if (mediaMuxer != null) {
                    mediaMuxer.stop();
                    mediaMuxer.release();
                }
                if (videoExtractor != null) {
                    videoExtractor.release();
                }
                if (audioExtractor != null) {
                    audioExtractor.release();
                }
            } catch (Exception e) {
                Log.w(TAG, "combineMedia release ex", e);
            }
        }

        return false;
    }

    private int prepareMediaInfo(MediaExtractor mediaExtractor, MediaMuxer mediaMuxer,
                                 int mediaTrack, boolean isMedia) {
        try {
            // change to video oraudio track 切换道视频或音频信号的信道
            mediaExtractor.selectTrack(mediaTrack);
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(mediaTrack);

            //追踪此信道
            int writeTrackIndex = mediaMuxer.addTrack(trackFormat);
            mediaMuxer.start();

            return writeTrackIndex;

        } catch (Exception e) {
            Log.w(TAG, "prepareMediaInfo ex", e);
        }

        return 0;
    }

    /**
     * write sample data to mediaMuxer
     *
     * @param mediaExtractor
     * @param mediaMuxer
     * @param writeTrackIndex
     * @param audioTrack
     * @return
     */
    private boolean writeSampleData(MediaExtractor mediaExtractor, MediaMuxer mediaMuxer,
                                    int writeTrackIndex, int audioTrack) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(ALLOCATE_BUFFER);

            // 读取写入帧数据
            long sampleTime = getSampleTime(mediaExtractor, byteBuffer, audioTrack);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

            while (true) {
                //读取帧之间的数据
                int readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0);
                if (readSampleSize < 0) {
                    break;
                }

                mediaExtractor.advance();
                bufferInfo.size = readSampleSize;
                bufferInfo.offset = 0;
                bufferInfo.flags = mediaExtractor.getSampleFlags();
                bufferInfo.presentationTimeUs += sampleTime;
                //写入帧的数据
                mediaMuxer.writeSampleData(writeTrackIndex, byteBuffer, bufferInfo);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "writeSampleData ex", e);
        }

        return false;
    }

    /**
     * @param mediaExtractor
     * @param isMedia        true: get "video/"
     *                       false get "audio/"
     * @return
     */
    private int getTrack(MediaExtractor mediaExtractor, boolean isMedia) {
        if (mediaExtractor == null) {
            Log.w(TAG, "mediaExtractor mediaExtractor is null");
            return 0;
        }
        String type = isMedia ? "video/" : "audio/";
        int trackCount = mediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
            String mineType = trackFormat.getString(MediaFormat.KEY_MIME);
            // video or audio track
            if (mineType.startsWith(type)) {
                return i;
            }
        }

        return 0;
    }

    /**
     * 获取每帧的之间的时间
     *
     * @return
     */
    private long getSampleTime(MediaExtractor mediaExtractor, ByteBuffer byteBuffer, int videoTrack) {
        if (mediaExtractor == null) {
            Log.w(TAG, "getSampleTime mediaExtractor is null");
            return 0;
        }
        mediaExtractor.readSampleData(byteBuffer, 0);
        //skip first I frame
        if (mediaExtractor.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC) {
            mediaExtractor.advance();
        }
        mediaExtractor.readSampleData(byteBuffer, 0);

        // get first and second and count sample time
        long firstVideoPTS = mediaExtractor.getSampleTime();
        mediaExtractor.advance();
        mediaExtractor.readSampleData(byteBuffer, 0);
        long SecondVideoPTS = mediaExtractor.getSampleTime();
        long sampleTime = Math.abs(SecondVideoPTS - firstVideoPTS);
        Log.d(TAG, "getSampleTime is " + sampleTime);

        // 重新切换此信道，不然上面跳过了3帧,造成前面的帧数模糊
        mediaExtractor.unselectTrack(videoTrack);
        mediaExtractor.selectTrack(videoTrack);

        return sampleTime;
    }

}
