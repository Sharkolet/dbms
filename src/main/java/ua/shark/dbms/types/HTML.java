package ua.shark.dbms.types;

import java.io.File;
import java.io.Serializable;

public class HTML implements Serializable{
	private File html;

    public HTML(File html) {
        this.html = html;
    }

    public File getHTML() {
        return html;
    }

    public void setHTML(File html) {
        this.html = html;
    }
}
