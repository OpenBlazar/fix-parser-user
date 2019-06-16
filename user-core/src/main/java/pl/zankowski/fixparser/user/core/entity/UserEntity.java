package pl.zankowski.fixparser.user.core.entity;

import pl.zankowski.fixparser.core.entity.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "user")
public class UserEntity extends AbstractBaseEntity {

    @NotNull
    @Pattern(regexp = "^[_'.@A-Za-z0-9-]*$")
    @Size(min = 1, max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String username;

    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @Size(min = 60, max = 60)
    @Column(length = 60)
    private String password;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    private String activationKey;

    @Column
    private boolean activated;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate;

    public UserEntity() {
    }

    public UserEntity(
            final String createdBy,
            final Instant createdDate,
            final String lastModifiedBy,
            final Instant lastModifiedDate,
            final Long id,
            final String username,
            final String email,
            final String password,
            final String activationKey,
            final boolean activated,
            final String resetKey,
            final Instant resetDate) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate, id);
        this.username = username;
        this.email = email;
        this.password = password;
        this.activationKey = activationKey;
        this.activated = activated;
        this.resetKey = resetKey;
        this.resetDate = resetDate;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getResetKey() {
        return resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(final UserEntity userEntity) {
        Objects.requireNonNull(userEntity);
        return new Builder(userEntity);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final UserEntity that = (UserEntity) o;
        return activated == that.activated &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(activationKey, that.activationKey) &&
                Objects.equals(resetKey, that.resetKey) &&
                Objects.equals(resetDate, that.resetDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, email, password, activationKey,
                activated, resetKey, resetDate);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", activationKey='" + activationKey + '\'' +
                ", activated=" + activated +
                ", resetKey='" + resetKey + '\'' +
                ", resetDate=" + resetDate +
                "} " + super.toString();
    }

    public static final class Builder {

        private Long id;
        private String username;
        private String email;
        private String password;
        private String activationKey;
        private boolean activated;
        private String resetKey;
        private Instant resetDate;

        public Builder() {
        }

        public Builder(final UserEntity entity) {
            this.id = entity.getId();
            this.username = entity.getUsername();
            this.email = entity.getEmail();
            this.password = entity.getPassword();
            this.activationKey = entity.getActivationKey();
            this.activated = entity.isActivated();
            this.resetKey = entity.getResetKey();
            this.resetDate = entity.getResetDate();
        }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder username(final String username) {
            this.username = username;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder password(final String password) {
            this.password = password;
            return this;
        }

        public Builder activationKey(final String activationKey) {
            this.activationKey = activationKey;
            return this;
        }

        public Builder activated(final boolean activated) {
            this.activated = activated;
            return this;
        }

        public Builder resetKey(final String resetKey) {
            this.resetKey = resetKey;
            return this;
        }

        public Builder resetDate(final Instant resetDate) {
            this.resetDate = resetDate;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(null, null, null, null, id,
                    username, email, password, activationKey, activated, resetKey, resetDate);
        }

    }
}
