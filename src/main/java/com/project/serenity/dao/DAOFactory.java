package com.project.serenity.dao;

import com.project.serenity.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOTypes {
        THERAPIST,
        THERAPYPROGRAM,
        PATIENT,
        PAYMENT,THERAPYSESSION
    }

    public SuperDAO getDAO(DAOTypes type) {
        switch (type) {
            case THERAPIST:
                return  new TherapistDAOImpl();
            case THERAPYPROGRAM:
                return  new TherapyProgramDAOImpl();
                case PATIENT:
                    return new PatientDAOImpl();
               case PAYMENT:
                  return new PaymentDAOImpl();
               case THERAPYSESSION:
                   return new TherapySessionDAOImpl();
                    default:
                return null;
        }
    }
}
