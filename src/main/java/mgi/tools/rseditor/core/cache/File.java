package mgi.tools.rseditor.core.cache;

import mgi.tools.rseditor.core.utilities.ByteBuffer;

public class File {

    /**
     * Contains file ID.
     */
    private int id;
    /**
     * Contains name hash of this file.
     */
    private int name;
    /**
     * Contains file data if loaded.
     */
    private ByteBuffer data;
    /**
     * Wheter filesystem info about this file
     * changed in any way.
     */
    private boolean fsInfoChanged;
    /**
     * Wheter file data was changed.
     */
    private boolean dataChanged;

    public File() {

    }

    /**
     * This constructor can be used only by FS
     * loader.
     */
    public File(int fileID, int name) {
        this(fileID, name, null);
    }


    /**
     * Construct's new file with autoassigned ID.
     */
    public File(String name, ByteBuffer data) {
        this(-1, Helper.strToI(name), data);
    }

    /**
     * Construct's new file with autoassigned ID.
     */
    public File(ByteBuffer data) {
        this(-1, -1, data);
    }

    public File(int filID, ByteBuffer data) {
        this(filID, -1, data);
    }

    public File(int fileID, String name, ByteBuffer data) {
        this(fileID, Helper.strToI(name), data);
    }

    public File(int fileID, int name, ByteBuffer data) {
        this.id = fileID;
        this.name = name;
        this.data = data;
    }

    /**
     * Copies this file, including the
     * buffer if it's present.
     */
    public File copy() {
        File f = new File(id, name);
        if (data != null) {
            f.data = new ByteBuffer(data.toArray(0, data.getBuffer().length), data.getPosition());
        }
        return f;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        if (!isLoaded())
            throw new RuntimeException("Using nonloaded file.");
        if (this.id != id) {
            this.id = id;
            fsInfoChanged = true;
        }
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        if (!isLoaded())
            throw new RuntimeException("Using nonloaded file.");

        if (this.name != name) {
            fsInfoChanged = true;
            this.name = name;
        }
    }

    public void setName(String name) {
        setName(Helper.strToI(name));
    }

    public ByteBuffer getData() {
        if (!isLoaded())
            throw new RuntimeException("Using nonloaded file.");
        return data;
    }

    /**
     * Set's file data.
     */
    public void setData(ByteBuffer data) {
        if (!isLoaded())
            throw new RuntimeException("Using nonloaded file.");
        if (this.data != data) {
            dataChanged = fsInfoChanged = true;
            this.data = data;
        }
    }

    /**
     * Load's this file.
     */
    public void load(ByteBuffer data) {
        if (isLoaded())
            throw new RuntimeException("Already loaded.");
        this.data = data;
    }

    /**
     * Wheter this file is loaded.
     */
    public boolean isLoaded() {
        return data != null;
    }

    /**
     * Unload's this file , can be called
     * only by folder unload() method.
     */
    public void unload() {
        if (!isLoaded())
            throw new RuntimeException("Using nonloaded file.");
        data = null;
    }

    /**
     * Whether filesystem info about this file changed.
     */
    public boolean isFileSystemInfoChanged() {
        return fsInfoChanged;
    }

    /**
     * Wheter data was changed.
     */
    public boolean isDataChanged() {
        return dataChanged;
    }

    /**
     * Mark's this file as not changed.
     */
    public void markFileSystemInfoAsNotChanged() {
        fsInfoChanged = false;
    }

    /**
     * Mark's data as not changed.
     */
    public void markDataAsNotChanged() {
        dataChanged = false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        File other = (File) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
