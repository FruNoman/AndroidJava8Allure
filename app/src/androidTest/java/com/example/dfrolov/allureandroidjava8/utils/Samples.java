package com.example.dfrolov.allureandroidjava8.utils;

public enum Samples {
    jpgI000287("I_000287_benchmark_lightbox_5336x3000_16MP.jpg"),
    jpgI000309("I_000309_roskilde_station.jpg"),
    jpgI000310("I_000310_kettbach_dulmen.jpg"),
    jpgI000311("I_000311_konnu_suursoo.jpg"),
    jpgI000001("I_000001_Birdcatcher_with_jockey_up.jpg"),
    webpI000308("I_000308_green_mountain.webp"),
    gifI000021("I_000021_GPN_001040.gif"),
    bmpI000312("I_000312_moon_landscape.bmp"),
    pngI000301("I_000301_1922_world_map_34995x2374.png"),
    pngI000313("I_000313_niagara_falls.png");

    String path;

    Samples(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}
