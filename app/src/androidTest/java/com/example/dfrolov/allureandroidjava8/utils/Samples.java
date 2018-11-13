package com.example.dfrolov.allureandroidjava8.utils;

public enum Samples {
    // IMAGE SAMPLES ->
    jpgI000287("I_000287_benchmark_lightbox_5336x3000_16MP.jpg"),
    jpgI000309("I_000309_roskilde_station.jpg"),
    jpgI000310("I_000310_kettbach_dulmen.jpg"),
    jpgI000311("I_000311_konnu_suursoo.jpg"),
    jpgI000001("I_000001_Birdcatcher_with_jockey_up.jpg"),
    webpI000308("I_000308_green_mountain.webp"),
    gifI000021("I_000021_GPN_001040.gif"),
    bmpI000312("I_000312_moon_landscape.bmp"),
    pngI000301("I_000301_1922_world_map_34995x2374.png"),
    pngI000313("I_000313_niagara_falls.png"),


    // VIDEO SAMPLES ->
    h263AV001248("AV_001248_lunarsurface_H263_CIF_2mbps_30fps_NB_AMR.mp4"),
    h264AV000869("AV_000869_H264_High_Profile_level_31_VGA_30fps_4Mbps_AAC_48kHz_128kbps_stereo.mp4"),
    h264AV001097("AV_001097_1080p_crowdrun_HP_cabac_2B_wBpred_adct_30fps_20mbps_1000fr_AAC_HE_48kHz_64kbps_stereo.mp4"),
    h264AV001187("AV_001187_Toy_Story3Official_Trailer_in_720p_h264_MP_L2_1280x720_24fps_1Mbps_eAACplus_64kbps_44100Hz.mp4"),
    h265AV001429("AV_001429_life_in_orbit_720p_main_2.mp4"),
    h265AV001430("AV_001430_iss_tour_720p_main_3.mp4"),
    h265AV001431("AV_001431_arthur_christmas_720p_psnr_main_22.mp4"),
    mpeg4AV000022("AV_000022_SF145.mp4"),
    mp3A000001("A_000001_02_Tetanus.mp3"),
    wavA000354("A_000354_PCM_16bit_48Khz_1536kbps_stereo.wav"),
    flacA000484("A_000484_SpoonRevenge.flac"),
    sample3gpA000123("A_000123_15dot85kbps_ex30.3gp"),
    aacA000157("A_000157_128kbps_werk32.aac"),
    amrA000443("A_000443_NB_AMR_8000Hz_10.2kbps.AMR"),
    mp4A000454("A_000454_eAACplus_48000Hz_32Kbps_Stereo_track1.mp4"),
    oggA000480("A_000480_The_Abyss-4T.ogg"),
    mkaA000485("A_000485_ehren-paper_lights.mka");


    String path;

    Samples(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}
