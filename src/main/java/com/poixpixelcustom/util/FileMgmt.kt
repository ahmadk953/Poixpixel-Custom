package com.poixpixelcustom.util

import com.poixpixelcustom.PoixpixelCustom
import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream
import org.apache.commons.compress.utils.IOUtils
import java.io.*
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object FileMgmt {
    private val readWriteLock: ReadWriteLock = ReentrantReadWriteLock()
    private val readLock = readWriteLock.readLock()
    private val writeLock = readWriteLock.writeLock()

    /**
     * Checks a folderPath to see if it exists, if it doesn't it will attempt
     * to create the folder at the designated path.
     *
     * @param folderPath [String] containing a path to a folder.
     * @return True if the folder exists or if it was successfully created.
     */
    fun checkOrCreateFolder(folderPath: String?): Boolean {
        val file = File(folderPath)
        return if (file.exists() || file.isDirectory) {
            true
        } else newDir(file)
    }

    /**
     * Checks an array of folderPaths to see if they exist, if they don't
     * it will try to create the folder at the designated paths.
     *
     * @param folders array of [String] containing a path to a folder.
     * @return true or false
     */
    fun checkOrCreateFolders(vararg folders: String?): Boolean {
        for (folder in folders) {
            if (!checkOrCreateFolder(folder)) {
                return false
            }
        }
        return true
    }

    /**
     * Checks a filePath to see if it exists, if it doesn't it will attempt
     * to create the file at the designated path.
     *
     * @param filePath [String] containing a path to a file.
     * @return True if the folder exists or if it was successfully created.
     */
    fun checkOrCreateFile(filePath: String?): Boolean {
        val file = File(filePath)
        if (!checkOrCreateFolder(file.parentFile.path)) {
            return false
        }
        return if (file.exists()) {
            true
        } else newFile(file)
    }

    private fun newDir(dir: File): Boolean {
        return try {
            writeLock.lock()
            dir.mkdirs()
        } finally {
            writeLock.unlock()
        }
    }

    private fun newFile(file: File): Boolean {
        return try {
            writeLock.lock()
            file.createNewFile()
        } catch (e: IOException) {
            false
        } finally {
            writeLock.unlock()
        }
    }

    /**
     * Checks an array of folderPaths to see if they exist, if they don't
     * it will try to create the folder at the designated paths.
     *
     * @param files array of [String] containing a path to a file.
     * @return true or false
     */
    fun checkOrCreateFiles(vararg files: String?): Boolean {
        for (file in files) {
            if (!checkOrCreateFile(file)) {
                return false
            }
        }
        return true
    }

    // http://www.java-tips.org/java-se-tips/java.io/how-to-copy-a-directory-from-one-location-to-another-loc.html
    @Throws(IOException::class)
    fun copyDirectory(sourceLocation: File, targetLocation: File) {
        try {
            writeLock.lock()
            if (sourceLocation.isDirectory) {
                if (!targetLocation.exists()) targetLocation.mkdir()
                val children = sourceLocation.list()
                for (aChildren in children) copyDirectory(File(sourceLocation, aChildren), File(targetLocation, aChildren))
            } else {
                val out: OutputStream = FileOutputStream(targetLocation)
                try {
                    val `in`: InputStream = FileInputStream(sourceLocation)
                    // Copy the bits from in stream to out stream.
                    val buf = ByteArray(1024)
                    var len: Int
                    while (`in`.read(buf).also { len = it } > 0) out.write(buf, 0, len)
                    `in`.close()
                    out.close()
                } catch (ex: IOException) {
                    // failed to access file.
                    PoixpixelCustom.Companion.getPlugin().getLogger().warning("Error: Could not access: $sourceLocation")
                }
                out.close()
            }
        } finally {
            writeLock.unlock()
        }
    }

    /**
     * Write a list to a file, terminating each line with a system specific new line.
     *
     * @param source - Data source
     * @param targetLocation - Target location on Filesystem
     * @return true on success, false on IOException
     */
    fun listToFile(source: Collection<String>, targetLocation: String?): Boolean {
        try {
            writeLock.lock()
            val file = File(targetLocation)
            try {
                OutputStreamWriter(FileOutputStream(file), StandardCharsets.UTF_8).use { osw ->
                    BufferedWriter(osw).use { bufferedWriter ->
                        for (aSource in source) {
                            bufferedWriter.write(aSource + System.getProperty("line.separator"))
                        }
                        return true
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }
        } finally {
            writeLock.unlock()
        }
    }

    // move a file to a sub directory
    fun moveFile(sourceFile: File, targetLocation: String) {
        try {
            writeLock.lock()
            if (sourceFile.isFile) {
                // check for an already existing file of that name
                val f = File(sourceFile.parent + File.separator + targetLocation + File.separator + sourceFile.name)
                if (f.exists() && f.isFile) f.delete()
                // Move file to new directory
                sourceFile.renameTo(File(sourceFile.parent + File.separator + targetLocation, sourceFile.name))
            }
        } finally {
            writeLock.unlock()
        }
    }

    fun moveTownBlockFile(sourceFile: File, targetLocation: String, townDir: String) {
        try {
            writeLock.lock()
            if (sourceFile.isFile) {
                if (!townDir.isEmpty()) checkOrCreateFolder(sourceFile.parent + File.separator + "deleted" + File.separator + townDir) else checkOrCreateFolder(sourceFile.parent + File.separator + "deleted")
                // check for an already existing file of that name
                val f = File(sourceFile.parent + File.separator + targetLocation + File.separator + townDir + File.separator + sourceFile.name)
                if (f.exists() && f.isFile) f.delete()
                // Move file to new directory
                sourceFile.renameTo(File(sourceFile.parent + File.separator + targetLocation + File.separator + townDir, sourceFile.name))
            }
        } finally {
            writeLock.unlock()
        }
    }

    val fileTimeStamp: String
        get() {
            val t = System.currentTimeMillis()
            return SimpleDateFormat("yyyy-MM-dd HH-mm").format(t)
        }

    @Throws(IOException::class)
    fun tar(destination: File?, vararg sources: File) {
        try {
            readLock.lock()
            TarArchiveOutputStream(GzipCompressorOutputStream(FileOutputStream(destination))).use { archive ->
                archive.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX)
                for (source in sources) {
                    Files.walk(source.toPath()).forEach { path: Path ->
                        val file = path.toFile()
                        if (!file.isDirectory) {
                            val entry_1 = TarArchiveEntry(file, file.toString())
                            try {
                                FileInputStream(file).use { fis ->
                                    archive.putArchiveEntry(entry_1)
                                    IOUtils.copy(fis, archive)
                                    archive.closeArchiveEntry()
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
        } finally {
            readLock.unlock()
        }
    }

    /**
     * Zip a given file into the given path.
     *
     * @param file - File to zip.
     * @param path - Path to put file.
     */
    fun zipFile(file: File, path: String?) {
        try {
            ZipOutputStream(FileOutputStream(path), StandardCharsets.UTF_8).use { zos ->
                writeLock.lock()
                val buffer = ByteArray(2056) // Buffer with which to write the bytes of the zip file.
                zos.putNextEntry(ZipEntry(file.name)) // Place file into zip.
                FileInputStream(file).use { `in` ->
                    var len: Int
                    while (`in`.read(buffer).also { len = it } > 0) { // While there is data to write, write up to the buffer.
                        zos.write(buffer, 0, len)
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            writeLock.unlock()
        }
    }

    @Throws(IOException::class)
    fun zipDirectories(destination: File?, vararg sourceFolders: File) {
        try {
            readLock.lock()
            val output = ZipOutputStream(FileOutputStream(destination), StandardCharsets.UTF_8)
            for (sourceFolder in sourceFolders) recursiveZipDirectory(sourceFolder, output)
            output.close()
        } finally {
            readLock.unlock()
        }
    }

    @Throws(IOException::class)
    fun recursiveZipDirectory(sourceFolder: File, zipStream: ZipOutputStream) {
        try {
            readLock.lock()
            val dirList = sourceFolder.list()
            val readBuffer = ByteArray(2156)
            var bytesIn: Int
            for (aDirList in dirList) {
                val f = File(sourceFolder, aDirList)
                if (f.isDirectory) {
                    recursiveZipDirectory(f, zipStream)
                } else if (f.isFile && f.canRead()) {
                    val input = FileInputStream(f)
                    val anEntry = ZipEntry(f.path)
                    zipStream.putNextEntry(anEntry)
                    while (input.read(readBuffer).also { bytesIn = it } != -1) zipStream.write(readBuffer, 0, bytesIn)
                    input.close()
                }
            }
        } finally {
            readLock.unlock()
        }
    }

    /**
     * Delete file, or if path represents a directory, recursively
     * delete it's contents beforehand.
     *
     * @param file - [File] to delete
     */
    fun deleteFile(file: File) {
        try {
            writeLock.lock()
            if (file.isDirectory) {
                var children = file.listFiles()
                if (children != null) {
                    for (child in children) deleteFile(child)
                }
                children = file.listFiles()
                if (children == null || children.size == 0) {
                    if (!file.delete()) PoixpixelCustom.Companion.getPlugin().getLogger().warning("Error: Could not delete folder: " + file.path)
                }
            } else if (file.isFile) {
                if (!file.delete()) PoixpixelCustom.Companion.getPlugin().getLogger().warning("Error: Could not delete file: " + file.path)
            }
        } finally {
            writeLock.unlock()
        }
    }

    /**
     * Delete child files/folders of backupsDir with a filename ending
     * in milliseconds that is older than deleteAfter milliseconds in age.
     *
     * @param backupsDir - [File] path to backupsDir
     * @param deleteAfter - Maximum age of files, in milliseconds
     * @return Whether old backups were successfully deleted.
     */
    fun deleteOldBackups(backupsDir: File, deleteAfter: Long): Boolean {
        return try {
            writeLock.lock()
            val deleted = TreeSet<Long>()
            if (backupsDir.isDirectory) {
                val children = backupsDir.listFiles()
                if (children != null) {
                    for (child in children) {
                        try {
                            var filename = child.name
                            if (child.isFile) {
                                if (filename.contains(".")) filename = filename.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                            }
                            val tokens = filename.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            val lastToken = tokens[tokens.size - 1]
                            val timeMade = lastToken.toLong()
                            if (timeMade >= 0) {
                                val age = System.currentTimeMillis() - timeMade
                                if (age >= deleteAfter) {
                                    deleteFile(child)
                                    deleted.add(age)
                                }
                            }
                        } catch (e: Exception) {
                            // Ignore file as it doesn't follow the backup format.
                        }
                    }
                }
            } else return false
            if (deleted.size > 0) {
                PoixpixelCustom.Companion.getPlugin().getLogger().info(String.format("Deleting %d Old Backups (%s).", deleted.size, if (deleted.size > 1) String.format("%d-%d days old", TimeUnit.MILLISECONDS.toDays(deleted.first()), TimeUnit.MILLISECONDS.toDays(deleted.last())) else String.format("%d days old", TimeUnit.MILLISECONDS.toDays(deleted.first()))))
            }
            true
        } finally {
            writeLock.unlock()
        }
    }

    /**
     * Function which reads from a resident, town, nation, PoixpixelCustomObject file, returning a hashmap.
     *
     * @param file - File from which the HashMap will be made.
     * @return HashMap - Used for loading keys and values from object files.
     */
    fun loadFileIntoHashMap(file: File?): HashMap<String, String> {
        return try {
            readLock.lock()
            val keys = HashMap<String, String>()
            try {
                FileInputStream(file).use { fis ->
                    InputStreamReader(fis, StandardCharsets.UTF_8).use { isr ->
                        val properties = Properties()
                        properties.load(isr)
                        for (key in properties.stringPropertyNames()) {
                            val value = properties.getProperty(key)
                            keys[key] = value.toString()
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            keys
        } finally {
            readLock.unlock()
        }
    }

    @Throws(IOException::class)
    fun writeString(path: Path, string: String) {
        FileOutputStream(path.toFile()).use { fos -> fos.write(string.toByteArray(StandardCharsets.UTF_8)) }
    }
}