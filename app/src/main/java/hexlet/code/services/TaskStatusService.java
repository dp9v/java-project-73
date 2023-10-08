package hexlet.code.services;

import hexlet.code.dtos.TaskStatusDto;
import hexlet.code.repositories.TaskStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskStatusService {
    private final TaskStatusRepository repository;

    public List<TaskStatusDto> getAll() {
        return repository.findAll()
            .stream()
            .map(TaskStatusDto::new)
            .toList();
    }
}
