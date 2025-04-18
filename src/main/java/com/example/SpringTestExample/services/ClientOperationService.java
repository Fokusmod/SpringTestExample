package com.example.SpringTestExample.services;

import com.example.SpringTestExample.dto.OperationMessage;
import com.example.SpringTestExample.dto.WalletOperationRequest;
import com.example.SpringTestExample.exception.BadRequestException;
import com.example.SpringTestExample.exception.NotFoundException;
import com.example.SpringTestExample.models.Wallet;
import com.example.SpringTestExample.repositories.WallerRepository;
import com.example.SpringTestExample.utils.enums.TypeOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ClientOperationService {
    @Autowired
    private WallerRepository wallerRepository;
    private final ReentrantLock lock = new ReentrantLock();
    private final Logger logger = LoggerFactory.getLogger(ClientOperationService.class);


    public Wallet getCurrentWallet(String uuid) {
        UUID id;
        try {
            id = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            logger.debug("При проверке баланса указан неверный uuid кошелька.");
            throw new BadRequestException("Неверный формат uuid, проверьте корректность ввода данных.");
        }
        Wallet wallet = wallerRepository.findByClientId(id);
        if (wallet == null) {
            logger.debug("Кошелек с указанным uuid не найден");
            throw new NotFoundException("Кошелёк с указанным uuid не найден. Проверьте корректность ввода данных");
        }
        logger.info("Получен баланс счета пользователя.");
        return wallet;
    }


    public ResponseEntity<?> walletOperation(WalletOperationRequest request) {
        if (request == null || request.getOperationType() == null || request.getWalletUUID() == null || request.getAmount() == null) {
            logger.debug("Переданный запрос на выполнение операций пополнения или изъятия средств является некорректным. " +
                    "Возможно отсутствуют некоторые данные.");
            throw new BadRequestException("Некорректный формат запроса. Убедитесь что запрос не пустой и переданные " +
                    "вами данные содержат корректные значения");
        }
        String replace = request.getAmount().toString().replace("-", "");
        request.setAmount(new BigDecimal(replace));
        lock.lock();
        try {
            if (request.getOperationType().equals(TypeOperation.DEPOSIT.toString())) {
                return walletDepositOperation(request);
            } else if (request.getOperationType().equals(TypeOperation.WITHDRAW.toString())) {
                return walletWithdrawOperation(request);
            } else {
                logger.debug("Запрос содержит неверные данные об необходимой операции.");
                throw new BadRequestException("Введенный вами тип операции не поддерживается либо является некорректным");
            }
        } finally {
            lock.unlock();
        }
    }

    private ResponseEntity<OperationMessage> walletWithdrawOperation(WalletOperationRequest request) {
        Wallet wallet = getCurrentWallet(request.getWalletUUID());
        if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
            logger.debug("Недостаточно средств на счёте.");
            throw new BadRequestException("Недостаточно средств на счёте. Операция отменена.");
        }
        BigDecimal total = wallet.getBalance().subtract(request.getAmount());
        wallet.setBalance(total);
        wallerRepository.save(wallet);
        logger.info("Операция списания средств со счёта успешна проведена.");
        return new ResponseEntity<>(new OperationMessage("Денежные средства были успешно списаны."),HttpStatus.OK);
    }

    private ResponseEntity<OperationMessage> walletDepositOperation(WalletOperationRequest request) {
        Wallet wallet = getCurrentWallet(request.getWalletUUID());
        BigDecimal total = wallet.getBalance().add(request.getAmount());
        wallet.setBalance(total);
        wallerRepository.save(wallet);
        logger.info("Операция внесения средств на счёт успешна проведена.");
        return new ResponseEntity<>(new OperationMessage("Денежные средства были успешно внесены на счет."),HttpStatus.OK);
    }


}
