package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

/**
 * An entity class for mapping {@value ActorInfo#VIEW_NAME} view.
 * <p>
 * <blockquote>
 * The {@value #VIEW_NAME} view provides a list of all actors, including the films in which they have performed, broken
 * down by category.<br/>The {@value StaffList#VIEW_NAME} view incorporates data from the
 * {@value Film#TABLE_NAME}, {@value Actor#TABLE_NAME}, {@value Category#TABLE_NAME}, {@value FilmActor#TABLE_NAME}, and
 * {@value FilmCategory#TABLE_NAME} tables.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-views-actor_info.html">5.2.1 The actor_info
 * View</a>
 */
@Entity
@Table(name = ActorInfo.VIEW_NAME)
public class ActorInfo
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String VIEW_NAME = "actor_info";

    public static final String COLUMN_NAME_ACTOR_ID = Actor.COLUMN_NAME_ACTOR_ID;

    public static final String COLUMN_NAME_FIRST_NAME = Actor.COLUMN_NAME_FIRST_NAME;

    public static final String COLUMN_NAME_LAST_NAME = Actor.COLUMN_NAME_LAST_NAME;

    /**
     * Creates a new instance.
     */
    protected ActorInfo() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "actorId=" + actorId +
               ",firstName=" + firstName +
               ",lastName=" + lastName +
               ",filmInfo=" + filmInfo +
               '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ActorInfo that)) return false;
        if (!super.equals(obj)) return false;
        return Objects.equals(actorId, that.actorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId);
    }

    @Override
    Integer identifier() {
        return actorId;
    }

    public Integer getActorId() {
        return actorId;
    }

    @Deprecated(forRemoval = true)
    private void setActorId(final Integer actorId) {
        this.actorId = actorId;
    }

    public String getFirstName() {
        return firstName;
    }

    @Deprecated(forRemoval = true)
    private void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Deprecated(forRemoval = true)
    private void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFilmInfo() {
        return filmInfo;
    }

    @Deprecated(forRemoval = true)
    private void setFilmInfo(final String filmInfo) {
        this.filmInfo = filmInfo;
    }

    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ACTOR_ID, nullable = false)
    private Integer actorId;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FIRST_NAME, nullable = false, length = 45)
    private String firstName;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LAST_NAME, nullable = false, length = 45)
    private String lastName;

    @Basic(optional = true)
    @Column(name = "film_info", nullable = true, length = _PersistenceConstants.COLUMN_LENGTH_TEXT)
    private String filmInfo;
}
