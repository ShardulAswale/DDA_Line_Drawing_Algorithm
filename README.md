# DDA Line Drawing

Java OpenGL demonstration of rasterising lines and polygon outlines using DDA-style incremental coordinates.

## How it works

The drawing routine increments x and y according to the dominant line length and plots points. Additional routines draw thick or dotted edges, which are combined into sample polygons.

## Usage

Requires a Java Development Kit, a desktop display and a legacy JOGL installation compatible with the `javax.media.opengl` API. Configure the JOGL JARs and native libraries in the Java classpath, then compile `DDALine.java` and run `raster.DDALine`.

## Notes

The source declares package `raster` despite being stored at the repository root. Compile with an output directory that preserves the package structure. Rendering parameters are fixed in the source, and coordinate-rounding behaviour requires review.
