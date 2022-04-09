package cn.chuanwise.panda.stored;

import cn.chuanwise.common.util.ByteStreams;
import cn.chuanwise.common.util.Files;
import cn.chuanwise.common.util.Preconditions;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.beans.Transient;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Objects;

@Getter
public class StoredFile {
    @NonNull
    @Setter
    protected transient Serializer serializer = Serializer.of(getClass());
    
    @Setter
    protected transient File file;
    
    @Setter
    @NonNull
    protected transient Charset charset = Charset.defaultCharset();
    
    @Transient
    public Serializer getSerializer() {
        return serializer;
    }
    
    @Transient
    public File getFile() {
        return file;
    }
    
    @Transient
    public Charset getCharset() {
        return charset;
    }
    
    public void save(File file, Serializer serializer, Charset charset) throws IOException {
        Preconditions.nonNull(file, "file");
        Preconditions.nonNull(serializer, "serializer");
        Preconditions.nonNull(charset, "charset");
        
        final String string = serializer.serialize(this);
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(string.getBytes(charset));
        }
    }
    
    public void save(File file, Charset charset) throws IOException {
        save(file, getSerializer(), charset);
    }
    
    public void save(File file, Serializer serializer) throws IOException {
        save(file, serializer, getCharset());
    }
    
    public void save(Serializer serializer) throws IOException {
        save(getFile(), serializer, getCharset());
    }
    
    public void save(Charset charset) throws IOException {
        save(getFile(), getSerializer(), charset);
    }
    
    public void save(File file) throws IOException {
        save(file, getSerializer(), getCharset());
    }
    
    public void save() throws IOException {
        save(getFile(), getSerializer(), getCharset());
    }
    
    public static <T extends StoredFile> T loadFile(File file, Charset charset, Class<T> clazz) throws IOException {
        Preconditions.nonNull(file, "file");
        Preconditions.nonNull(charset, "charset");
        Preconditions.nonNull(clazz, "class");
        
        final Serializer serializer = Serializer.of(clazz);
        final String string = Files.readString(file, charset);
        
        final T data = serializer.deserialize(string, clazz);
        data.serializer = serializer;
        data.file = file;
        data.charset = charset;
        return data;
    }
    
    public static <T extends StoredFile> T loadFile(File file, Class<T> clazz) throws IOException {
        return loadFile(file, Charset.defaultCharset(), clazz);
    }
    
    public static <T extends StoredFile> T loadResource(String path, Charset charset, Class<T> clazz) throws IOException {
        Preconditions.nonNull(path, "path");
        Preconditions.nonNull(charset, "charset");
        Preconditions.nonNull(clazz, "class");
        
        final InputStream inputStream = clazz.getClassLoader().getResourceAsStream(path);
        Preconditions.element(Objects.nonNull(inputStream), "no such resource: " + path + " in class loader of class: " + clazz);
        final String string = new String(ByteStreams.read(inputStream), charset);
        
        final Serializer serializer = Serializer.of(clazz);
        final T data = serializer.deserialize(string, clazz);
        data.serializer = serializer;
        data.charset = charset;
        return data;
    }
    
    public static <T extends StoredFile> T loadResource(String path, Class<T> clazz) throws IOException {
        return loadResource(path, Charset.defaultCharset(), clazz);
    }
}