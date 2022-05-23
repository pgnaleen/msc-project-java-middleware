package com.ubs.sis.admin.form.repository.specification;

import com.ubs.sis.admin.form.domain.custom_form.Form;
import com.ubs.sis.admin.form.domain.custom_form.FormDefinition;
import com.ubs.sis.admin.form.domain.custom_form.FormDefinition_;
import com.ubs.sis.admin.form.domain.custom_form.Form_;
import com.ubs.sis.admin.form.domain.enums.Status;
import com.ubs.sis.admin.form.dto.FormDefinitionDescriptor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class FormSpecification {
    public static <T> Specification<Form> withStatus(Status status) {
        if (status == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get(Form_.status), status);
    }

    public static Specification<Form> withFormDef(FormDefinitionDescriptor formDefinitionDescriptor) {
        if (formDefinitionDescriptor == null) {
            return null;
        }
        return (root, query, cb) -> {
            Join<Form, FormDefinition> gateway = root.join(Form_.formDefinition);
            return cb.and(
                    cb.equal(gateway.get(FormDefinition_.businessEntity), formDefinitionDescriptor.getBusinessEntity()),
                    formDefinitionDescriptor.getAssignedBusinessEntity() != null ?
                            cb.equal(gateway.get(FormDefinition_.assignedBusinessEntity), formDefinitionDescriptor.getAssignedBusinessEntity()) :
                            cb.isNull(gateway.get(FormDefinition_.assignedBusinessEntity))
            );
        };
    }

    public static Specification<Form> withFormDef(Long formDefinitionId) {
        if (formDefinitionId == null) {
            return null;
        }
        return (root, query, cb) -> {
            Join<Form, FormDefinition> gateway = root.join(Form_.formDefinition);
            return
                    cb.equal(gateway.get(FormDefinition_.id), formDefinitionId);

        };
    }


}

