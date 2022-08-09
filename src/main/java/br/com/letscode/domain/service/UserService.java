package br.com.letscode.domain.service;

import br.com.letscode.domain.entity.User;
import br.com.letscode.domain.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
@Transactional
@Traced
@Slf4j
public class UserService {

    @Inject
    UserRepository userRepository;

    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    public User save(User user) {
        String encodePassword = BcryptUtil.bcryptHash(user.getPassword());
        return this.userRepository.save(user.toBuilder().password(encodePassword).build());
    }

    public User update(User user) {
        return this.userRepository.save(user);
    }

    public void delete(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            this.userRepository.delete(user.get());
        }
    }
}
