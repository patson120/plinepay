package com.plinepay.core.repositories;

import com.plinepay.core.entities.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Repository
public class EntityManagerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Retrieve all Privilege of Database
     *
     * @return
     */
    public List<Privilege> retrievePrivilege() {
        String queryString = "SELECT p FROM Privilege p";
        Query query = entityManager.createQuery(queryString, Privilege.class);
        return query.getResultList();
    }

    /**
     * Retrieve all Privilege of Database
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<Privilege> retrievePrivilegeLimited(int firstResult, int maxResults) {
        String queryString = "SELECT p FROM Privilege p";
        Query query = entityManager.createQuery(queryString, Privilege.class);
        if (maxResults != 0) {
            query = exractData(query, firstResult, maxResults);
        }
        return query.getResultList();
    }

    /**
     * Retrieve all Resource of Database
     *
     * @return
     */
    public List<Resource> retrieveResource() {
        String queryString = "SELECT r FROM Resource r";
        Query query = entityManager.createQuery(queryString, Resource.class);
        return query.getResultList();
    }

    /**
     * Retrieve all Resource of Database
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<Resource> retrieveResourceLimited(int firstResult, int maxResults) {
        String queryString = "SELECT r FROM Resource r";
        Query query = entityManager.createQuery(queryString, Resource.class);
        if (maxResults != 0) {
            query = exractData(query, firstResult, maxResults);
        }
        return query.getResultList();
    }

    /**
     * Retrieve all Resource of Database
     *
     * @return
     */
    public List<Role> retrieveRole() {
        String queryString = "SELECT r FROM Role r";
        Query query = entityManager.createQuery(queryString, Role.class);
        return query.getResultList();
    }

    /**
     * Retrieve all Role of Database
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<Role> retrieveRoleLimited(int firstResult, int maxResults) {
        String queryString = "SELECT r FROM Role r";
        Query query = entityManager.createQuery(queryString, Role.class);
        if (maxResults != 0) {
            query = exractData(query, firstResult, maxResults);
        }
        return query.getResultList();
    }

    /**
     * Retrieve all Trader Account of Database
     *
     * @return
     */
    public List<TraderAccount> retrieveTraderAccount() {
        String queryString = "SELECT t FROM TraderAccount t";
        Query query = entityManager.createQuery(queryString, TraderAccount.class);
        return query.getResultList();
    }

    /**
     * Retrieve all Trader Account of Database
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<TraderAccount> retrieveTraderAccountLimited(int firstResult, int maxResults) {
        String queryString = "SELECT t FROM TraderAccount t";
        Query query = entityManager.createQuery(queryString, TraderAccount.class);
        if (maxResults != 0) {
            query = exractData(query, firstResult, maxResults);
        }
        return query.getResultList();
    }

    /**
     * Retrieve all User of Database
     *
     * @return
     */
    public List<User> retrieveUser() {
        String queryString = "SELECT u FROM User u";
        Query query = entityManager.createQuery(queryString, User.class);
        return query.getResultList();
    }

    /**
     * Retrieve all User of Database
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<User> retrieveUserLimited(int firstResult, int maxResults) {
        String queryString = "SELECT u FROM User u";
        Query query = entityManager.createQuery(queryString, User.class);
        if (maxResults != 0) {
            query = exractData(query, firstResult, maxResults);
        }
        return query.getResultList();
    }

    /**
     * Retrieve all Updated User of Database
     *
     * @param lastDateUpdate
     * @return
     */
    public List<User> retrieveUserUpdated(long lastDateUpdate) {
        String queryString = "SELECT u FROM User u WHERE u.lastDateUpdate > :lastDateUpdate";
        Query query = entityManager.createQuery(queryString, User.class);
        query.setParameter("lastDateUpdate", lastDateUpdate);
        return query.getResultList();
    }

    /**
     * Retrieve all Updated Role of Database
     *
     * @param lastDateUpdate
     * @return
     */
    public List<Role> retrieveRoleUpdated(long lastDateUpdate) {
        String queryString = "SELECT r FROM Role r WHERE r.lastDateUpdate > :lastDateUpdate";
        Query query = entityManager.createQuery(queryString, Role.class);
        query.setParameter("lastDateUpdate", lastDateUpdate);
        return query.getResultList();
    }

    /**
     * Retrieve all Updated TraderAccount of Database
     *
     * @param lastDateUpdate
     * @return
     */
    public List<TraderAccount> retrieveTraderAccountUpdated(long lastDateUpdate) {
        String queryString = "SELECT t FROM TraderAccount t WHERE t.lastDateUpdate > :lastDateUpdate";
        Query query = entityManager.createQuery(queryString, TraderAccount.class);
        query.setParameter("lastDateUpdate", lastDateUpdate);
        return query.getResultList();
    }

    /**
     * Retrieve all Privilege of Database
     *
     * @param roleId
     * @return
     */
    public List<Privilege> retrievePrivilegeByRole(UUID roleId) {
        String queryString = "SELECT p FROM Privilege p WHERE p.role.id = :roleId";
        Query query = entityManager.createQuery(queryString, Privilege.class);
        query.setParameter("roleId", roleId);
        return query.getResultList();
    }

    /**
     * Retrieve all Privilege of Database
     *
     * @param resourceId
     * @return
     */
    public List<Privilege> retrievePrivilegeByResource(UUID resourceId) {
        String queryString = "SELECT p FROM Privilege p WHERE p.resource.id = :resourceId ORDER BY p.role.name ASC";
        Query query = entityManager.createQuery(queryString, Privilege.class);
        query.setParameter("resourceId", resourceId);
        return query.getResultList();
    }

    /**
     * Retrieve all Privilege of Database
     *
     * @param resourceId
     * @param roleId
     * @return
     */
    public List<Privilege> retrievePrivilegeByResourceAndRole(UUID resourceId, UUID roleId) {
        String queryString = "SELECT p FROM Privilege p WHERE p.resource.id = :resourceId AND p.role.id = :roleId";
        Query query = entityManager.createQuery(queryString, Privilege.class);
        query.setParameter("resourceId", resourceId);
        query.setParameter("roleId", roleId);
        return query.getResultList();
    }

    /**
     * Retrieve all Setting of Database
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<Setting> retrieveSettingLimited(int firstResult, int maxResults) {
        String queryString = "SELECT s FROM Setting s";
        Query query = entityManager.createQuery(queryString, Setting.class);
        if (maxResults != 0) {
            query = exractData(query, firstResult, maxResults);
        }
        return query.getResultList();
    }

    /**
     * Retrieve all Updated Setting of Database
     *
     * @param lastDateUpdate
     * @return
     */
    public List<Setting> retrieveSettingUpdated(long lastDateUpdate) {
        String queryString = "SELECT s FROM Setting s WHERE s.lastDateUpdate > :lastDateUpdate";
        Query query = entityManager.createQuery(queryString, Setting.class);
        query.setParameter("lastDateUpdate", lastDateUpdate);
        return query.getResultList();
    }

    /**
     * Retrieve all Setting of Database
     *
     * @return
     */
    public List<Setting> retrieveSetting() {
        String queryString = "SELECT s FROM Setting s";
        Query query = entityManager.createQuery(queryString, Setting.class);
        return query.getResultList();
    }

    /**
     * Retrieve all MobileDeposit of Database
     *
     * @return
     */
    public List<MobileDeposit> retrieveMobileDeposit() {
        String queryString = "SELECT m FROM MobileDeposit m";
        Query query = entityManager.createQuery(queryString, MobileDeposit.class);
        return query.getResultList();
    }

    /**
     * Retrieve all Updated MobileDeposit of Database
     *
     * @param lastDateUpdate
     * @return
     */
    public List<MobileDeposit> retrieveMobileDepositUpdated(long lastDateUpdate) {
        String queryString = "SELECT m FROM MobileDeposit m WHERE m.lastDateUpdate > :lastDateUpdate";
        Query query = entityManager.createQuery(queryString, MobileDeposit.class);
        query.setParameter("lastDateUpdate", lastDateUpdate);
        return query.getResultList();
    }

    /**
     * Retrieve all MobileDeposit of Database
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<MobileDeposit> retrieveMobileDepositLimited(int firstResult, int maxResults) {
        String queryString = "SELECT m FROM MobileDeposit m";
        Query query = entityManager.createQuery(queryString, MobileDeposit.class);
        if (maxResults != 0) {
            query = exractData(query, firstResult, maxResults);
        }
        return query.getResultList();
    }

    /**
     * Retrieve all OnlinePayment of Database
     *
     * @return
     */
    public List<OnlinePayment> retrieveOnlinePayment() {
        String queryString = "SELECT o FROM OnlinePayment o";
        Query query = entityManager.createQuery(queryString, OnlinePayment.class);
        return query.getResultList();
    }

    /**
     * Retrieve all Updated OnlinePayment of Database
     *
     * @param lastDateUpdate
     * @return
     */
    public List<OnlinePayment> retrieveOnlinePaymentUpdated(long lastDateUpdate) {
        String queryString = "SELECT m FROM OnlinePayment m WHERE m.lastDateUpdate > :lastDateUpdate";
        Query query = entityManager.createQuery(queryString, OnlinePayment.class);
        query.setParameter("lastDateUpdate", lastDateUpdate);
        return query.getResultList();
    }

    /**
     * Retrieve all OnlinePayment of Database
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<OnlinePayment> retrieveOnlinePaymentLimited(int firstResult, int maxResults) {
        String queryString = "SELECT o FROM OnlinePayment o";
        Query query = entityManager.createQuery(queryString, OnlinePayment.class);
        if (maxResults != 0) {
            query = exractData(query, firstResult, maxResults);
        }
        return query.getResultList();
    }

    /**
     * Retrieve all Transfer of Database
     *
     * @return
     */
    public List<Transfer> retrieveTransfer() {
        String queryString = "SELECT t FROM Transfer t";
        Query query = entityManager.createQuery(queryString, Transfer.class);
        return query.getResultList();
    }

    /**
     * Retrieve all Updated Transfer of Database
     *
     * @param lastDateUpdate
     * @return
     */
    public List<Transfer> retrieveTransferUpdated(long lastDateUpdate) {
        String queryString = "SELECT t FROM Transfer t WHERE t.lastDateUpdate > :lastDateUpdate";
        Query query = entityManager.createQuery(queryString, Transfer.class);
        query.setParameter("lastDateUpdate", lastDateUpdate);
        return query.getResultList();
    }

    /**
     * Retrieve all Transfer of Database
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<Transfer> retrieveTransferLimited(int firstResult, int maxResults) {
        String queryString = "SELECT t FROM Transfer t";
        Query query = entityManager.createQuery(queryString, Transfer.class);
        if (maxResults != 0) {
            query = exractData(query, firstResult, maxResults);
        }
        return query.getResultList();
    }

    /**
     * Retrieve all Virement of Database
     *
     * @return
     */
    public List<Virement> retrieveVirement() {
        String queryString = "SELECT v FROM Virement v";
        Query query = entityManager.createQuery(queryString, Virement.class);
        return query.getResultList();
    }

    /**
     * Retrieve all Updated Virement of Database
     *
     * @param lastDateUpdate
     * @return
     */
    public List<Virement> retrieveVirementUpdated(long lastDateUpdate) {
        String queryString = "SELECT v FROM Virement v WHERE v.lastDateUpdate > :lastDateUpdate";
        Query query = entityManager.createQuery(queryString, Virement.class);
        query.setParameter("lastDateUpdate", lastDateUpdate);
        return query.getResultList();
    }

    /**
     * Retrieve all Virement of Database
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<Virement> retrieveVirementLimited(int firstResult, int maxResults) {
        String queryString = "SELECT v FROM Virement v";
        Query query = entityManager.createQuery(queryString, Virement.class);
        if (maxResults != 0) {
            query = exractData(query, firstResult, maxResults);
        }
        return query.getResultList();
    }

    /**
     * Retrieve all Application of Database
     *
     * @return
     */
    public List<Application> retrieveApplication() {
        String queryString = "SELECT a FROM Application a";
        Query query = entityManager.createQuery(queryString, Application.class);
        return query.getResultList();
    }

    /**
     * Retrieve all Updated Virement of Database
     *
     * @param lastDateUpdate
     * @return
     */
    public List<Application> retrieveApplicationUpdated(long lastDateUpdate) {
        String queryString = "SELECT a FROM Application a WHERE a.lastDateUpdate > :lastDateUpdate";
        Query query = entityManager.createQuery(queryString, Application.class);
        query.setParameter("lastDateUpdate", lastDateUpdate);
        return query.getResultList();
    }

    /**
     * Retrieve all Virement of Database
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<Application> retrieveApplicationLimited(int firstResult, int maxResults) {
        String queryString = "SELECT a FROM Application a";
        Query query = entityManager.createQuery(queryString, Application.class);
        if (maxResults != 0) {
            query = exractData(query, firstResult, maxResults);
        }
        return query.getResultList();
    }

    /**
     * Retrieve all MobileAccount of Database
     *
     * @return
     */
    public List<MobileAccount> retrieveMobileAccount() {
        String queryString = "SELECT m FROM MobileAccount m";
        Query query = entityManager.createQuery(queryString, MobileAccount.class);
        return query.getResultList();
    }

    /**
     * Retrieve all Updated MobileAccount of Database
     *
     * @param lastDateUpdate
     * @return
     */
    public List<MobileAccount> retrieveMobileAccountUpdated(long lastDateUpdate) {
        String queryString = "SELECT m FROM MobileAccount m WHERE m.lastDateUpdate > :lastDateUpdate";
        Query query = entityManager.createQuery(queryString, MobileAccount.class);
        query.setParameter("lastDateUpdate", lastDateUpdate);
        return query.getResultList();
    }

    /**
     * Retrieve all Virement of Database
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<MobileAccount> retrieveMobileAccountLimited(int firstResult, int maxResults) {
        String queryString = "SELECT m FROM MobileAccount m";
        Query query = entityManager.createQuery(queryString, MobileAccount.class);
        if (maxResults != 0) {
            query = exractData(query, firstResult, maxResults);
        }
        return query.getResultList();
    }

    /**
     * Retrieve all PaymentMethod of Database
     *
     * @return
     */
    public List<PaymentMethod> retrievePaymentMethod() {
        String queryString = "SELECT p FROM PaymentMethod p";
        Query query = entityManager.createQuery(queryString, PaymentMethod.class);
        return query.getResultList();
    }

    /**
     * Retrieve all Updated PaymentMethod of Database
     *
     * @param lastDateUpdate
     * @return
     */
    public List<PaymentMethod> retrievePaymentMethodUpdated(long lastDateUpdate) {
        String queryString = "SELECT p FROM PaymentMethod p WHERE p.lastDateUpdate > :lastDateUpdate";
        Query query = entityManager.createQuery(queryString, PaymentMethod.class);
        query.setParameter("lastDateUpdate", lastDateUpdate);
        return query.getResultList();
    }

    /**
     * Retrieve all PaymentMethod of Database
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<PaymentMethod> retrievePaymentMethodLimited(int firstResult, int maxResults) {
        String queryString = "SELECT p FROM PaymentMethod p";
        Query query = entityManager.createQuery(queryString, PaymentMethod.class);
        if (maxResults != 0) {
            query = exractData(query, firstResult, maxResults);
        }
        return query.getResultList();
    }

    /**
     * Méthode permettant l'extraction des données de page d'une requête données
     * 
     * @param query
     * @param firstResult
     * @param maxResults
     * @return
     */
    private Query exractData(Query query, int firstResult, int maxResults) {
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query;
    }

}
