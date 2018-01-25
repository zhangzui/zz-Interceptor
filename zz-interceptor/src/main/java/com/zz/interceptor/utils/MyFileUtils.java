package com.zz.interceptor.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipOutputStream;

/**
 * 文件工具
 * @author peng.liu
 * @create 2011-1-17
 */
public class MyFileUtils {
	static final Log log = LogFactory.getLog(MyFileUtils.class);
	/**
	 * 文件是否存在
	 * @param path
	 * @return
	 */
	public static boolean exist(String path) {
		File file = new File(path);
		return file.exists();
	}
	/**
	 * 得到文件的写入writer
	 * @param path
	 * @return
	 */
	public static Writer getFileWriter(String path)  {
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			return null;
		}
		try {
			return new FileWriter(path);
		} catch (IOException e) {
			throw new RuntimeException(MyFileUtils.class + "#getFileWriter IOException", e);
		}
	}
	/**
	 * 得到文件
	 * @param path
	 * @return
	 */
	public static File getFile(String path) {
		return getFile(path, false);
	}
	/**
	 * 得到文件，checkExist为true时不存在则抛出异常
	 * @param path
	 * @param checkExist
	 * @return
	 */
	public static File getFile(String path, boolean checkExist) {
		File f = new File(path);
		if (checkExist && !f.exists()) {
			throw new RuntimeException("File[" + path + "] not exist!");
		}
		return f;
	}
	/**
	 * 移动文件
	 * @param src
	 * @param dest
	 */
	public static void mv(File src, File dest) {
		try {
			if (!src.exists() || src.getCanonicalPath().equals(dest.getCanonicalPath())) {
				return;
			}
		} catch (IOException ex) {
		}
		src.renameTo(dest);
	}
	/**
	 * 删除文件
	 * @param f
	 */
	public static void rm(File f) {
		if (f.exists()) {
			f.delete();
		}
	}
	/**
	 * 复制文件
	 * @param srcPath
	 * @param destPath
	 */
	public static void cp(String srcPath, String destPath) {
		cp(new File(srcPath), new File(destPath));
	}
	/**
	 * 复制文件
	 * @param src
	 * @param dest
	 */
	public static void cp(File src, File dest) {
		FileInputStream in = null;
		FileOutputStream out = null;
		if (!src.exists()) {
			if (log.isInfoEnabled()) {
				log.info(MyFileUtils.class.getSimpleName() + " cp src:" + src.getAbsolutePath() + " not exist");
			}
			return;
		}
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			in = new FileInputStream(src);
			out = new FileOutputStream(dest);
			FileChannel inChannel = in.getChannel();
			FileChannel outChannel = out.getChannel();
			int write = 0;
			long read = inChannel.size();
			while (write < read) {
				write += inChannel.transferTo(write, read - write, outChannel);
			}
		} catch (FileNotFoundException e) {
			log.error(MyFileUtils.class.getName() + "#cp FileNotFoundException", e);
		} catch (IOException e) {
			log.error(MyFileUtils.class.getName() + "#cp IOException", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				log.error(MyFileUtils.class.getName() + "#cp close in IOException", e);
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				log.error(MyFileUtils.class.getName() + "#cp close out IOException", e);
			}
		}
	}

	/**
	 * 得到某个目录下符合条件的文件
	 * 
	 * @param dirPath
	 * @param filter
	 * @param recursive
	 * @return
	 */
	public static Collection<File> filterFiles(String dirPath, FileFilter filter, boolean recursive) {
		File parentDir = getFile(dirPath);
		if (!parentDir.exists() || parentDir.isFile()) {
			return null;
		}
		File[] fileArray = parentDir.listFiles();
		if (ArrayUtils.isEmpty(fileArray)) {
			return null;
		}
		List<File> returnValue = new ArrayList<File>(0);
		for (File f : fileArray) {
			if (f.isDirectory() && recursive) {
				Collection<File> subList = filterFiles(f.getAbsolutePath(), filter, recursive);
				if (subList != null) {
					returnValue.addAll(subList);
				}
			} else if (f.isFile() && filter.accept(f)) {
				returnValue.add(f);
			}
		}
		return returnValue;
	}

	/**
	 * 判断给定的路径是否为目录
	 * 
	 * @param dirpath
	 * @return
	 */
	public static boolean isDirectory(String dirpath) {
		File dir = new File(dirpath);
		return dir.isDirectory();
	}

	/**
	 * 列出某个目录下的文件(按照filter进行过滤,最多列出max个文件)
	 * 
	 * @param filter
	 * @param max
	 * @return
	 */
	public static File[] listFiles(String dirpath, FilenameFilter filter, int max) {
		if (!isDirectory(dirpath)) {
			return null;
		}
		File dir = new File(dirpath);
		File[] files = dir.listFiles(filter);
		if (ArrayUtils.isEmpty(files)) {
			return null;
		}
		Arrays.sort(files);
		int end = max < files.length ? max : files.length;
		return (File[]) ArrayUtils.subarray(files, 0, end);
	}
	/**
	 * 创建文件夹
	 * @param dir
	 * @return
	 */
	public static boolean mkdir(String dir) {
		File file = new File(dir);
		return mkdir(file);
	}
	/**
	 * 创建文件夹
	 * @param dir
	 * @return
	 */
	public static boolean mkdir(File dir) {
		if (dir.exists() && dir.isDirectory()) {
			return true;
		}
		return dir.mkdirs();
	}
	/**
	 * 得到文件内容
	 * @param file
	 * @return
	 */
	public static String getContent(File file) {
		StringBuffer sb = new StringBuffer();
		if (file.exists() && file.isFile()) {
			BufferedReader r = null;
			try {
				r = new BufferedReader(new FileReader(file));
				char[] buff = new char[4096];
				int read = 0;
				while ((read = r.read(buff)) != -1) {
					sb.append(buff, 0, read);
				}
			} catch (Exception e) {
				log.error(MyFileUtils.class.getName(), e);
			} finally {
				if (r != null) {
					try {
						r.close();
					} catch (IOException e) {
						log.error(MyFileUtils.class.getName(), e);
					}
				}
			}
		}
		return sb.toString();
	}
	/**
	 * 写文件
	 * @param file
	 * @param s
	 */
	public static void writeContent(File file, String s) {
		Writer w = null;
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			w = new FileWriter(file);
			w.write(s);
			w.flush();
		} catch (Exception e) {
			log.error(MyFileUtils.class.getName(), e);
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
					log.error(MyFileUtils.class.getName(), e);
				}
			}
		}
	}
	/**
	 * 写文件
	 * @param f
	 * @param s
	 * @param decode
	 * @param encode
	 */
	public static void writeContent(File f, String s, String decode, String encode) {
		if (StringUtils.isNotEmpty(decode) && StringUtils.isNotEmpty(encode) && !decode.equals(encode)) {
			OutputStream out = null;
			try {
				if (!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
				}
				out = new FileOutputStream(f);
				String content = new String(s.getBytes(), decode);
				out.write(content.getBytes(encode));
			} catch (UnsupportedEncodingException e) {
				log.error(MyFileUtils.class.getSimpleName() + "#writeContent UnsupportedEncodingException", e);
			} catch (IOException e) {
				log.error(MyFileUtils.class.getSimpleName() + "#writeContent IOException", e);
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
					log.error(MyFileUtils.class.getSimpleName() + "#writeContent close io IOException", e);
				}
			}
		} else {
			writeContent(f, s);
		}
		writeContent(f, s);
	}

	/**
	 * 压缩文件
	 * @param fileName
	 *            source file name
	 * @param zipFileName
	 *            zip file name
	 * @param delSrc
	 *            delete source file or not
	 * @return success or not
	 */
	public static boolean zip(String fileName, String zipFileName, boolean delSrc) {
		File srcFile = new File(fileName);
		File zipFile = new File(zipFileName);
		if (!zipFile.getParentFile().exists()) {
			zipFile.getParentFile().mkdirs();
		}
		if (srcFile.isFile()) {
			FileInputStream is = null;
			FileOutputStream os = null;
			CheckedOutputStream cs = null;
			ZipOutputStream out = null;
			try {
				is = new FileInputStream(fileName);
				os = new FileOutputStream(zipFileName);
				cs = new CheckedOutputStream(os, new Adler32());
				out = new ZipOutputStream(new BufferedOutputStream(cs));
				out.putNextEntry(new java.util.zip.ZipEntry(srcFile.getName()));
				int b = -1;
				while ((b = is.read()) != -1) {
					out.write(b);
				}
				out.flush();
				if (delSrc) {
					srcFile.delete();
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(cs != null) {
					try {
						cs.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(os != null) {
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}
}
