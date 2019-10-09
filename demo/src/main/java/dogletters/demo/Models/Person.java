package dogletters.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="person")
public class Person {

    //creates unique primary key ID column
    //and auto increment with each new User
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //creates unique username column
    @Column(unique = true)
    private String username;

    //creates password column
    @Column
    private String password;

    @Column
    private String email;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="user_profile_id")
//    private PersonProfile personProfile;

    @OneToMany(
            mappedBy = "person",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(
            mappedBy = "person",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Comment> comments;

    // link with PersonRole
    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "person_role_id", nullable = false)
    private PersonRole personRole;

    // get person role
    public PersonRole getPersonRole() {
        return personRole;
    }

    // set person role
    public void setPersonRole(PersonRole personRole) {
        this.personRole = personRole;
    }

    public Person(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

//    public PersonProfile getPersonProfile() {
//        return personProfile;
//    }
//
//    public void setPersonProfile(PersonProfile userProfile) {
//        this.personProfile = personProfile;
//    }

    public List<Post> addPost(Post post){
        if (posts==null ){
            posts = new ArrayList<>();
        }
        posts.add(post);

        return posts;
    }

    public List<Comment> addComment(Comment comment){
        if (comment==null ){
            comments = new ArrayList<>();
        }
        comments.add(comment);

        return comments;
    }
}
