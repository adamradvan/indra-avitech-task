package eu.radvan.indraavitechtask.command;


import eu.radvan.indraavitechtask.model.User;
import eu.radvan.indraavitechtask.model.dto.CreateUserDto;
import eu.radvan.indraavitechtask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddCommand implements Command {
    private final UserRepository repository;
    private final CreateUserDto dto;

    public AddCommand(UserRepository repository, CreateUserDto dto) {
        this.repository = repository;
        this.dto = dto;
    }

    @Override
    public void execute() {
        User user = repository.create(dto);

        log.info("Added: " + user);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
