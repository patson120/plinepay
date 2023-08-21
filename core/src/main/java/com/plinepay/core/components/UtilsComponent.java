package com.plinepay.core.components;

import com.plinepay.core.dto.EntityDto;
import com.plinepay.core.entities.*;
import com.plinepay.core.repositories.*;
import com.plinepay.core.utils.ErrorService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilsComponent {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private MobileAccountRepository mobileAccountRepository;

    @Autowired
    private MobileDepositRepository mobileDepositRepository;

    @Autowired
    private OnlinePaymentRepository onlinePaymentRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TraderAccountRepository traderAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private VirementRepository virementRepository;

    /**
     * Find Payment By Id
     *
     * @param id
     * @return
     */
    public Payment findPaymentById(UUID id) {
        return virementRepository.findById(id).orElse(null);
    }

    /**
     * Find Privilege By Id
     *
     * @param id
     * @return
     */
    public Privilege findPrivilegeById(UUID id) {
        return privilegeRepository.findById(id).orElse(null);
    }

    /**
     * Find Resource By Id
     *
     * @param id
     * @return
     */
    public Resource findResourceById(UUID id) {
        return resourceRepository.findById(id).orElse(null);
    }

    /**
     * Find Role By Id
     *
     * @param id
     * @return
     */
    public Role findRoleById(UUID id) {
        return roleRepository.findById(id).orElse(null);
    }

    /**
     * Find TraderAccount By Id
     *
     * @param id
     * @return
     */
    public TraderAccount findTraderAccountById(UUID id) {
        return traderAccountRepository.findById(id).orElse(null);
    }

    /**
     * Find TraderAccount By public Key
     *
     * @param publicKey
     * @return
     */
    public TraderAccount findTraderAccountByPublicKey(String publicKey) {
        return traderAccountRepository.findByPublicKey(publicKey);
    }

    /**
     * Find User By Id
     *
     * @param id
     * @return
     */
    public User findUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     *
     * @param errors
     * @param entityDto
     */
    public void validate(ErrorService errors, EntityDto entityDto) {

    }

    /**
     * Cette méthode permet de calculer les frais de transaction
     * @param traderAccount
     * @param amount
     * @return
     */
    public Float buildFees(TraderAccount traderAccount, Float amount){
        //A implémenter
        return 0f;
    }

    /**
     * Find Setting By Id
     *
     * @param id
     * @return
     */
    public Setting findSettingById(UUID id) {
        return settingRepository.findById(id).orElse(null);
    }

    /**
     * Find Application By Id
     *
     * @param id
     * @return
     */
    public Application findApplicationById(UUID id) {
        return applicationRepository.findById(id).orElse(null);
    }

    /**
     * Find MobileAccount By Id
     *
     * @param id
     * @return
     */
    public MobileAccount findMobileAccountById(UUID id) {
        return mobileAccountRepository.findById(id).orElse(null);
    }


    /**
     * Find OnlinePayment By Id
     *
     * @param id
     * @return
     */
    public OnlinePayment findOnlinePaymentById(UUID id) {
        return onlinePaymentRepository.findById(id).orElse(null);
    }

    /**
     * Find PaymentMethod By Id
     *
     * @param id
     * @return
     */
    public PaymentMethod findPaymentMethodById(UUID id) {
        return paymentMethodRepository.findById(id).orElse(null);
    }

    /**
     * Find Transfer By Id
     *
     * @param id
     * @return
     */
    public Transfer findTransferById(UUID id) {
        return transferRepository.findById(id).orElse(null);
    }

    /**
     * Find Virement By Id
     *
     * @param id
     * @return
     */
    public Virement findVirementById(UUID id) {
        return virementRepository.findById(id).orElse(null);
    }

    /**
     * Find MobileDeposit By Id
     *
     * @param id
     * @return
     */
    public MobileDeposit findMobileDepositById(UUID id) {
        return mobileDepositRepository.findById(id).orElse(null);
    }

}
