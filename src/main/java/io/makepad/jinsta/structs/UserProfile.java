package io.makepad.jinsta.structs;

public class UserProfile extends BaseUser {
    private boolean isVisible;
    private BaseUser[] followers;
    private  BaseUser[] follows;
    private int nbFollowers;
    private int nbFollows;
    private String fullName;
    private String bio;
    private int nbPosts;

    public UserProfile(int followers, int follows, String fullName, String bio,String username, String profileLink, int posts, boolean isVisible){
        super(username,profileLink);
        this.bio = bio;
        this.nbFollowers = followers;
        this.nbFollows = follows;
        this.fullName = fullName;
        this.nbPosts = posts;
        this.isVisible = isVisible;

        if(isVisible){
            this.followers = new BaseUser[this.nbFollowers];
            this.follows = new BaseUser[this.nbFollows];
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public BaseUser[] getFollowers() {
        return followers;
    }

    public void setFollowers(BaseUser[] followers) {
        this.followers = followers;
    }

    public BaseUser[] getFollows() {
        return follows;
    }

    public void setFollows(BaseUser[] follows) {
        this.follows = follows;
    }

    public int getNbFollowers() {
        return nbFollowers;
    }

    public void setNbFollowers(int nbFollowers) {
        this.nbFollowers = nbFollowers;
    }

    public int getNbFollows() {
        return nbFollows;
    }

    public void setNbFollows(int nbFollows) {
        this.nbFollows = nbFollows;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getNbPosts() {
        return nbPosts;
    }

    public void setNbPosts(int nbPosts) {
        this.nbPosts = nbPosts;
    }
}
