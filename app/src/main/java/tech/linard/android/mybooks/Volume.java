package tech.linard.android.mybooks;

import android.text.TextUtils;

/**
 * Created by lucas on 29/11/16.
 */

public class Volume {
    private String mKind;
    private String mId;
    private String mEtag;
    private String mTitle;
    private String mSubtitle;
    private String mAuthors;
    private String mPublisher;
    private String mPublishedDate;
    private String mDescription;

    public String getmKind() {
        return mKind;
    }

    public void setmKind(String mKind) {
        this.mKind = mKind;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmEtag() {
        return mEtag;
    }

    public void setmEtag(String mEtag) {
        this.mEtag = mEtag;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        if (mTitle == null || TextUtils.isEmpty(mTitle)) {
            this.mTitle = "Title NOT FOUND";
        }
        this.mTitle = mTitle;
    }

    public String getmSubtitle() {
        return mSubtitle;
    }

    public void setmSubtitle(String mSubtitle) {
        this.mSubtitle = mSubtitle;
    }

    public String getmAuthors() {
        return mAuthors;
    }

    public void setmAuthors(String mAuthors) {
        if (mAuthors == null || TextUtils.isEmpty(mAuthors)) {
            this.mAuthors = "Author NOT FOUND";
        }
        this.mAuthors = mAuthors;
    }

    public String getmPublisher() {
        return mPublisher;
    }

    public void setmPublisher(String mPublisher) {
        this.mPublisher = mPublisher;
    }

    public String getmPublishedDate() {
        return mPublishedDate;
    }

    public void setmPublishedDate(String mPublishedDate) {
        this.mPublishedDate = mPublishedDate;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
};
