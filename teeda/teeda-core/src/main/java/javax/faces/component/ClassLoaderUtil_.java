package javax.faces.component;

import javax.faces.context.FacesContext;


class ClassLoaderUtil_ {

    private ClassLoaderUtil_(){
    }
    
    public static ClassLoader getClassLoader(FacesContext context){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(classLoader == null){
            classLoader = context.getClass().getClassLoader();
        }
        if(classLoader == null){
            classLoader = ClassLoaderUtil_.class.getClassLoader();
        }
        return classLoader;
    }

    public static Class loadClass(ClassLoader classLoader, String className) 
        throws ClassNotFoundException {
        return classLoader.loadClass(className);
    }
}
