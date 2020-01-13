package com.example.scientificCenter.security;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;

import ch.qos.logback.core.rolling.RollingFileAppender;

public class WorldWritableFileAppender extends RollingFileAppender {

	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		super.setFile(fileName);
		File f = new File(fileName);
		if (f.exists()) {
			java.nio.file.Files.setPosixFilePermissions(f.toPath(), EnumSet.allOf(PosixFilePermission.class));
		}
	}
}
