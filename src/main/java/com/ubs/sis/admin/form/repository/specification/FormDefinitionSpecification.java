package com.ubs.sis.admin.form.repository.specification;

import com.ubs.sis.admin.form.domain.custom_form.FormDefinition;
import com.ubs.sis.admin.form.domain.custom_form.FormDefinition_;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import org.springframework.data.jpa.domain.Specification;

public class FormDefinitionSpecification {

    public static Specification<FormDefinition> withBusinessEntities(BusinessEntity businessEntity, BusinessEntity assignedBusinessEntity) {

        return (root, query, cb) ->
             cb.and(
                    cb.equal(root.get(FormDefinition_.businessEntity), businessEntity),
                    cb.equal(root.get(FormDefinition_.assignedBusinessEntity), assignedBusinessEntity))
            ;

    }


//    public static Specification<CalendarEvent> eqCalendarEventType(CalendarEventType eventType) {
//    if (eventType == null) {
//        return null;
//    }
//    return (root, query, cb) -> cb.equal(root.get(CalendarEvent_.calendarEventType), eventType);
//}
//
//    public static Specification<CalendarEvent> active(Boolean active) {
//
//        return (root, query, cb) -> cb.equal(root.get(CalendarEvent_.active), active);
//    }
//
//    public static Specification<CalendarEvent> isTenantId(Long tenantId) {
//        if (tenantId == null) {
//            return null;
//        }
//        return (root, query, cb) -> cb.equal(root.get(CalendarEvent_.tenantId), tenantId);
//    }
//
//    public static Specification<CalendarEvent> eqMappedId(Long mappedId) {
//        if (mappedId == null) {
//            return null;
//        }
//        return (root, query, cb) -> cb.equal(root.get(CalendarEvent_.mappedId), mappedId);
//    }
//
//public static Specification<CalendarEventAssignee> eqAssignees(Set<Long> assignees) {
//    if (assignees == null || assignees.isEmpty()) {
//        return null;
//    }
//    return (root, query, cb) -> root.get(CalendarEventAssignee_.assigneeId).in(assignees);
//}
//
//    public static Specification<CalendarEventAssignee> withTenant(Long tenantId) {
//        if (tenantId == null) {
//            return null;
//        }
//        return (root, query, cb) -> {
//            Join<CalendarEventAssignee, CalendarEvent> gateway = root.join(CalendarEventAssignee_.calendarEvent);
//            return cb.equal(gateway.get(CalendarEvent_.tenantId), tenantId);
//        };
//    }
//
//    public static Specification<CalendarEventAssignee> withStartDate(Date startDate) {
//        if (startDate == null) {
//            return null;
//        }
//
//        return (root, query, cb) -> {
//            Join<CalendarEventAssignee, CalendarEvent> gateway = root.join(CalendarEventAssignee_.calendarEvent);
//
//            return cb.greaterThanOrEqualTo(gateway.get(CalendarEvent_.startTime), startDate);
//        };
//
//    }
//
//    public static Specification<CalendarEventAssignee> withEndDate(Date endDate) {
//        if (endDate == null) {
//            return null;
//        }
//        return (root, query, cb) -> {
//            Join<CalendarEventAssignee, CalendarEvent> gateway = root.join(CalendarEventAssignee_.calendarEvent);
//
//            return cb.lessThanOrEqualTo(gateway.get(CalendarEvent_.startTime), endDate);
//        };
//
//    }
//
//
//    public static Specification<CalendarEventAssignee> dismissed(Boolean dismissed) {
//        if (dismissed == null) {
//            return null;
//        }
//        return (root, query, cb) -> cb.equal(root.get(CalendarEventAssignee_.dismissed), dismissed);
//    }
//
//    public static Specification<CalendarEventAssignee> active(Boolean shouldActive) {
//        if (shouldActive == null) {
//            return null;
//        }
//        return (root, query, cb) -> {
//            Join<CalendarEventAssignee, CalendarEvent> gateway = root.join(CalendarEventAssignee_.calendarEvent);
//
//            return cb.and(cb.equal(gateway.get(CalendarEvent_.active), shouldActive),
//                    cb.equal(root.get(CalendarEventAssignee_.isActive), shouldActive));
//        };
//    }
//
//    public static Specification<CalendarEventAssignee> withPrivate(Boolean shouldPrivate) {
//        if (shouldPrivate == null) {
//            return null;
//        }
//        return (root, query, cb) -> {
//            Join<CalendarEventAssignee, CalendarEvent> gateway = root.join(CalendarEventAssignee_.calendarEvent);
//
//            return cb.equal(gateway.get(CalendarEvent_.isPrivate), shouldPrivate);
//        };
//    }
//
//
//    public static Specification<CalendarEventAssignee> withCalendarEventType(Collection<CalendarEventType> calendarEventType) {
//        if (calendarEventType == null|| calendarEventType.isEmpty()) {
//            return null;
//        }
//        return (root, query, cb) -> {
//            Join<CalendarEventAssignee, CalendarEvent> event = root.join(CalendarEventAssignee_.calendarEvent, JoinType.INNER);
//            return event.get(CalendarEvent_.calendarEventType).in(calendarEventType);
//        };
//    }
//
//    public static Specification<CalendarEventAssignee> withMappedIds(Collection<Long> mappedIds) {
//        if (mappedIds == null || mappedIds.isEmpty()) {
//            return null;
//        }
//        return (root, query, cb) -> {
//            Join<CalendarEventAssignee, CalendarEvent> event = root.join(CalendarEventAssignee_.calendarEvent, JoinType.INNER);
//            return event.get(CalendarEvent_.mappedId).in(mappedIds);
//        };
//    }
//
//    public static Specification<CalendarEventAssignee> withEventIds(Collection<Long> eventIds) {
//        if (eventIds == null || eventIds.isEmpty()) {
//            return null;
//        }
//        return (root, query, cb) -> root.get(CalendarEventAssignee_.calendarEventId).in(eventIds);
//    }


}

