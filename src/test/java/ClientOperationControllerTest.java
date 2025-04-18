import com.example.SpringTestExample.configuration.JavaConfig;
import com.example.SpringTestExample.controllers.ClientOperationController;
import com.example.SpringTestExample.dto.WalletOperationRequest;
import com.example.SpringTestExample.dto.WalletResponse;
import com.example.SpringTestExample.exception.BadRequestException;
import com.example.SpringTestExample.exception.NotFoundException;
import com.example.SpringTestExample.models.Wallet;
import com.example.SpringTestExample.repositories.WallerRepository;
import com.example.SpringTestExample.utils.enums.TypeOperation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JavaConfig.class})
@WebAppConfiguration
public class ClientOperationControllerTest {

    private MockMvc mockMvc;
    private Wallet testWallet;
    private WalletOperationRequest request;
    private ObjectMapper objectMapper;
    @Autowired
    private ClientOperationController walletController;
    @Autowired
    private WallerRepository wallerRepository;


    @Before
    public void setup() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
        testWallet = new Wallet();
        request = new WalletOperationRequest();
        testWallet.setClientId(UUID.randomUUID());
        testWallet.setBalance(BigDecimal.valueOf(1000.00).setScale(2, RoundingMode.HALF_UP));
        wallerRepository.save(testWallet);
    }

    @Test
    public void testGetWalletBalance_success() throws Exception {
        String answer = new WalletResponse(testWallet).toString();
        mockMvc.perform(get("/api/v1/wallets/{WALLET_UUID}", testWallet.getClientId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(answer));
    }

    @Test
    public void testGetWalletBalance_bad_request() {
        String badUuid = "invalid-uuid-format";
        String answer = "Неверный формат uuid, проверьте корректность ввода данных.";
        Assertions.assertThatThrownBy(() ->
                        mockMvc.perform(get("/api/v1/wallets/{WALLET_UUID}", badUuid))
                                .andExpect(status().isBadRequest()))
                .hasCause(new BadRequestException(answer));
    }

    @Test
    public void testGetWalletBalance_not_found() {
        String badUuid = testWallet.getClientId() + "1";
        String answer = "Кошелёк с указанным uuid не найден. Проверьте корректность ввода данных";
        Assertions.assertThatThrownBy(() ->
                        mockMvc.perform(get("/api/v1/wallets/{WALLET_UUID}", badUuid))
                                .andExpect(status().isNotFound()))
                .hasCause(new NotFoundException(answer));
    }

    @Test
    public void getWalletBalance_success_deposit() throws Exception {
        String answer = "{\"message\":\"Денежные средства были успешно внесены на счет.\"}";
        request.setOperationType(TypeOperation.DEPOSIT.toString());
        request.setAmount(new BigDecimal("1000.00"));
        request.setWalletUUID(testWallet.getClientId().toString());

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(answer));
    }

    @Test
    public void getWalletBalance_success_withdraw() throws Exception {
        String answer = "{\"message\":\"Денежные средства были успешно списаны.\"}";
        request.setOperationType(TypeOperation.WITHDRAW.toString());
        request.setAmount(new BigDecimal("1000.00"));
        request.setWalletUUID(testWallet.getClientId().toString());

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(answer));
    }

    @Test
    public void getWalletBalance_bad_request() throws Exception {
        String answer = "Введенный вами тип операции не поддерживается либо является некорректным";
        request.setOperationType(TypeOperation.DEPOSIT +" 1as");
        request.setAmount(new BigDecimal("1000.00"));
        request.setWalletUUID(testWallet.getClientId().toString());
        Assertions.assertThatThrownBy(() ->
                        mockMvc.perform(post("/api/v1/wallet")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest()))
                .hasCause(new BadRequestException(answer));
    }

    @Test
    public void getWalletBalance_nullable_request() throws Exception {
        String answer = "Некорректный формат запроса. Убедитесь что запрос не пустой и переданные вами " +
                "данные содержат корректные значения";
        request.setOperationType(null);
        request.setAmount(null);
        request.setWalletUUID(null);
        Assertions.assertThatThrownBy(() ->
                        mockMvc.perform(post("/api/v1/wallet")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest()))
                .hasCause(new BadRequestException(answer));
    }

    @Test
    public void getWalletBalance_not_enough_money() throws Exception {
        String answer = "Недостаточно средств на счёте. Операция отменена.";
        request.setOperationType(TypeOperation.WITHDRAW.toString());
        request.setAmount(BigDecimal.valueOf(100000.00));
        request.setWalletUUID(testWallet.getClientId().toString());
        Assertions.assertThatThrownBy(() ->
                        mockMvc.perform(post("/api/v1/wallet")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest()))
                .hasCause(new BadRequestException(answer));
    }

    @After
    public void removeTestEntity() {
        wallerRepository.delete(testWallet);
    }

}
