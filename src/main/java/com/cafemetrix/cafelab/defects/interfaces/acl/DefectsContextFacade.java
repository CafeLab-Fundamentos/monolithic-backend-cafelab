package com.cafemetrix.cafelab.defects.interfaces.acl;

import com.cafemetrix.cafelab.defects.domain.model.commands.CreateDefectCommand;

public interface DefectsContextFacade {

    Long createDefect(CreateDefectCommand command);
}
