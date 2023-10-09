package hexlet.code.services;

import hexlet.code.dtos.TaskStatusDto;
import hexlet.code.models.TaskStatus;
import hexlet.code.repositories.TaskStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskStatusService {
    private final TaskStatusRepository repository;

    public List<TaskStatus> getAll() {
        return repository.findAll();
    }

    public TaskStatus getById(long id) {
        return repository.findById(id)
            .orElseThrow();
    }

    public TaskStatus create(TaskStatusDto taskStatusDto) {
        var statusToSave = new TaskStatus()
            .setName(taskStatusDto.name());
        return repository.save(statusToSave);
    }

    public TaskStatus update(long id, TaskStatusDto taskStatusDto) {
        var statusToSave = repository.findById(id)
            .map(s->s.setName(taskStatusDto.name()))
            .orElseThrow();
        return repository.save(statusToSave);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
