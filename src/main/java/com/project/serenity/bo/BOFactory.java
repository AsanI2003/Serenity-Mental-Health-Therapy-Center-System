package com.project.serenity.bo;

import com.project.serenity.bo.custom.impl.*;


public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        THEPAPIST,THERAPYPROGRAM,PATIENT,PAYMENT,THERAPYSESSION
    }

    public SuperBO getBO(BOTypes boTypes) {
        switch (boTypes) {
            case THEPAPIST:
                return new TherapistBOImpl();
            case THERAPYPROGRAM:
                return new TherapyProgramBOImpl();
                case PATIENT:
                    return new PatientBOImpl();
                case PAYMENT:
                    return new PaymentBOImpl();
                case THERAPYSESSION:
                   return new TherapySessionBOImpl();
            default:
                return null;
        }
    }
}
