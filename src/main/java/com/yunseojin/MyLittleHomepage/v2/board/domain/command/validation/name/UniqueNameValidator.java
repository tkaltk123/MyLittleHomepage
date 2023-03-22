package com.yunseojin.MyLittleHomepage.v2.board.domain.command.validation.name;

import com.yunseojin.MyLittleHomepage.v2.board.domain.query.repository.QueriedBoardRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

    private final QueriedBoardRepository repository;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return isUniqueName(name);
    }

    private boolean isUniqueName(String name) {
        return !repository.existsByName(name);
    }
}
