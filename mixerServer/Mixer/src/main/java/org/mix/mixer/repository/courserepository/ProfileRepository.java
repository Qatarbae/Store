package org.mix.mixer.repository.courserepository;


import org.mix.mixer.entity.appuser.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
