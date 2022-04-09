package cn.chuanwise.panda.stored;

import cn.chuanwise.common.util.Preconditions;
import lombok.Data;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.introspector.BeanAccess;

public interface Serializer {
    String serialize(Object object);

    <T> T deserialize(String string, Class<T> clazz);

    Object deserialize(String string);

    @Data
    class Yaml implements Serializer {
        protected final org.yaml.snakeyaml.Yaml yaml;

        protected Yaml(ClassLoader classLoader) {
            Preconditions.nonNull(classLoader, "class loader");

            final Constructor constructor = new CustomClassLoaderConstructor(classLoader);
            this.yaml = new org.yaml.snakeyaml.Yaml(constructor);

            yaml.setBeanAccess(BeanAccess.FIELD);
        }

        protected Yaml(Class<?> clazz) {
            this(clazz.getClassLoader());
        }

        protected Yaml() {
            this.yaml = new org.yaml.snakeyaml.Yaml();
            yaml.setBeanAccess(BeanAccess.FIELD);
        }

        @Override
        public String serialize(Object object) {
            return yaml.dumpAsMap(object);
        }

        @Override
        public <T> T deserialize(String string, Class<T> clazz) {
            return yaml.loadAs(string, clazz);
        }

        @Override
        public Object deserialize(String string) {
            return yaml.load(string);
        }
    }

    static Serializer of() {
        return new Yaml();
    }

    static Serializer of(Class<?> clazz) {
        Preconditions.nonNull(clazz, "class");
        return new Yaml(clazz);
    }
}