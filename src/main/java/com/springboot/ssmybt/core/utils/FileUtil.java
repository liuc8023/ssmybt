package com.springboot.ssmybt.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.beetl.core.resource.WebAppResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import com.springboot.ssmybt.core.exception.SystemException;
import com.springboot.ssmybt.core.exception.SystemExceptionEnum;

public class FileUtil {
	 private static Logger log = LoggerFactory.getLogger(FileUtil.class);

	    /**
	     * NIO way
	     */
	    public static byte[] toByteArray(String filename) {

	        File f = new File(filename);
	        if (!f.exists()) {
	            log.error("文件未找到！" + filename);
	            throw new SystemException(SystemExceptionEnum.FILE_NOT_FOUND);
	        }
	        FileChannel channel = null;
	        FileInputStream fs = null;
	        try {
	            fs = new FileInputStream(f);
	            channel = fs.getChannel();
	            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
	            while ((channel.read(byteBuffer)) > 0) {
	                // do nothing
	                // System.out.println("reading");
	            }
	            return byteBuffer.array();
	        } catch (IOException e) {
	            throw new SystemException(SystemExceptionEnum.FILE_READING_ERROR);
	        } finally {
	            try {
	                channel.close();
	            } catch (IOException e) {
	                throw new SystemException(SystemExceptionEnum.FILE_READING_ERROR);
	            }
	            try {
	                fs.close();
	            } catch (IOException e) {
	                throw new SystemException(SystemExceptionEnum.FILE_READING_ERROR);
	            }
	        }
	    }

	    /**
	     * 删除目录
	     *
	     */
	    public static boolean deleteDir(File dir) {
	        if (dir.isDirectory()) {
	            String[] children = dir.list();
	            for (int i = 0; i < children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	        return dir.delete();
	    }
	    
	}