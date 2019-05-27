package library.services.modelservices;

import library.models.Action;
import library.repositories.ActionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionService {

    private ActionRepository actionRepository;

    public void addAction(Action action) {
        actionRepository.save(action);
    }

    private void updateAction(Action action, Integer actionId) {
        Action actionFromBase = actionRepository.getOne(actionId);

        actionRepository.save(action);
    }
}
