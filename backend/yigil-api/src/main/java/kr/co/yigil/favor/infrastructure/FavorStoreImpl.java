package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.Favor;
import kr.co.yigil.favor.domain.FavorStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavorStoreImpl implements FavorStore {
    private final FavorRepository favorRepository;

    @Override
    public void save(Favor favor) {
        favorRepository.save(favor);
    }

    @Override
    public void deleteFavorById(Long favorId) {
        favorRepository.deleteById(favorId);
    }
}
