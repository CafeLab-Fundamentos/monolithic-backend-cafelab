package com.cafemetrix.cafelab.defects.application.acl;

import com.cafemetrix.cafelab.defects.domain.model.commands.CreateDefectCommand;
import com.cafemetrix.cafelab.defects.domain.services.DefectCommandService;
import com.cafemetrix.cafelab.defects.interfaces.acl.DefectsContextFacade;
import org.springframework.stereotype.Service;

@Service
public class DefectsContextFacadeImpl implements DefectsContextFacade {
    private final DefectCommandService defectCommandService;

    public DefectsContextFacadeImpl(DefectCommandService defectCommandService) {
        this.defectCommandService = defectCommandService;
    }

    @Override
    public Long createDefect(CreateDefectCommand command) {
        var defect = defectCommandService.handle(command);
        return defect.isEmpty() ? 0L : defect.get().getId();
    }
}
