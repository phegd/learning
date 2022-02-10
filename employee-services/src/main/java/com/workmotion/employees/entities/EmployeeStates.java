package com.workmotion.employees.entities;

public enum EmployeeStates {
    ADDED(1, SubStates.NOT_APPLICABLE, SubStates.NOT_APPLICABLE),
    IN_CHECK(2, SubStates.SECURITY_CHECK_STARTED, SubStates.WORK_PERMIT_CHECK_STARTED),
    APPROVED(3, SubStates.NOT_APPLICABLE, SubStates.NOT_APPLICABLE),
    ACTIVE(4, SubStates.NOT_APPLICABLE, SubStates.NOT_APPLICABLE);

    private final Integer order;
    private final SubStates securityCheckState;
    private final SubStates workPermitCheckState;

    EmployeeStates(final Integer order, final SubStates securityCheckState, final SubStates workPermitCheckState) {
        this.order = order;
        this.securityCheckState = securityCheckState;
        this.workPermitCheckState = workPermitCheckState;
    }

    public Integer getOrder() {
        return order;
    }

    public Boolean isTransitionPermitted(EmployeeStates sourceState, EmployeeStates targetState, SubStates sourceSubState, SubStates targetSubState) {
        if ((targetState.getOrder() - sourceState.getOrder() == 1) ||
                (sourceSubState != null && EmployeeStates.IN_CHECK.equals(targetState)
                        && targetSubState.getCategory().equals(sourceSubState.getCategory())
                            && targetSubState.getOrder() > sourceSubState.getOrder())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public enum SubStates {
        NOT_APPLICABLE(0, "NA"),
        SECURITY_CHECK_STARTED(1, "SECURITY_CHECK"),
        SECURITY_CHECK_FINISHED(2, "SECURITY_CHECK"),
        WORK_PERMIT_CHECK_STARTED(3, "WORK_PERMIT_CHECK"),
        WORK_PERMIT_CHECK_FINISHED(4, "WORK_PERMIT_CHECK");

        private final Integer order;
        private final String category;

        SubStates(Integer order, String category) {
            this.order = order;
            this.category = category;
        }

        public Integer getOrder() {
            return order;
        }

        public String getCategory() {
            return category;
        }
    }
}
