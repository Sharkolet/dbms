package ua.shark.dbms.types;

import java.io.File;
import java.io.Serializable;

public class Picture implements Serializable {
	private File image;

    public Picture(File image) {
        this.image = image;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
