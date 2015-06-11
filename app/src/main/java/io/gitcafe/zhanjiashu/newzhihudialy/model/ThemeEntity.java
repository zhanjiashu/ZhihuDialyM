package io.gitcafe.zhanjiashu.newzhihudialy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ThemeEntity implements Parcelable {
    private String thumbnail;
    private int color;
    private String name;
    private String description;
    private int id;

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumbnail);
        dest.writeInt(this.color);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeInt(this.id);
    }

    public ThemeEntity() {
    }

    protected ThemeEntity(Parcel in) {
        this.thumbnail = in.readString();
        this.color = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<ThemeEntity> CREATOR = new Parcelable.Creator<ThemeEntity>() {
        public ThemeEntity createFromParcel(Parcel source) {
            return new ThemeEntity(source);
        }

        public ThemeEntity[] newArray(int size) {
            return new ThemeEntity[size];
        }
    };
}