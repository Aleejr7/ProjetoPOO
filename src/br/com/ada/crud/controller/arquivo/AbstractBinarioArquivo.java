package br.com.ada.crud.controller.arquivo;

import java.io.*;

public class AbstractBinarioArquivo<T> extends AbstractArquivo{

    public void escrever(T object, File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (IOException ex) {
            throw ex;
        }
    }

    public T ler(File file) throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Object object = objectInputStream.readObject();
            return (T) object;
        } catch (IOException | ClassNotFoundException ex) {
            throw ex;
        }
    }
}
