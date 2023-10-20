package com.tabsnotspaces.match;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.when;

import java.util.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class MatchingControllerTest {

    @InjectMocks
    private MatchingController matchingController;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void client() {
    }

    @Test
    void clientsListTest() {
        Client client1 = new Client();
        client1.setClientId(1);
        client1.setClientName("Client1");
        clientRepository.save(client1);

        Client client2 = new Client();
        client2.setClientId(2);
        client2.setClientName("Client2");
        clientRepository.save(client2);

        List<Client> clients = Arrays.asList(client1, client2);
        when(clientRepository.findAll()).thenReturn(clients);

        Iterable<Client> result = matchingController.clientsList();
        assertEquals(2, ((List<Client>) result).size());
    }

    @Test
    void clientsAdd() {
        ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
        Model model = Mockito.mock(Model.class);

        Client client = new Client();
        client.setClientId(1);
        client.setClientName("Client1");
        String name = "Client1";
        when(clientRepository.save(client)).thenReturn(client);
        Client result = matchingController.clientsAdd("Client1", model);
        assertEquals(result.getClientName(), name);
    }

    @Test
    void deleteClient() {
    }

    @Test
    void consumerAdd() {
        ConsumerRepository consumerRepository = Mockito.mock(ConsumerRepository.class);
        Model model = Mockito.mock(Model.class);
        Client client = new Client();
        client.setClientId(1);
        client.setClientName("Client1");
        String name = "Client1";
        when(clientRepository.save(client)).thenReturn(client);

        Consumer consumer = new Consumer();
        consumer.setConsumerId(1);
        consumer.setAddress("New York");
        consumer.setParentClientId(1);
        consumer.setAppointments(new ArrayList<Appointment>());
        when(consumerRepository.save(consumer)).thenReturn(consumer);
        Consumer result = matchingController.consumerAdd(1L, consumer);
        assertEquals(result, consumer);
    }

    @Test
    void serviceProviderAdd() {
        ServiceProviderRepository serviceProviderRepository = Mockito.mock(ServiceProviderRepository.class);
        Model model = Mockito.mock(Model.class);
        Client client = new Client();
        client.setClientId(1);
        client.setClientName("Client1");
        String name = "Client1";
        when(clientRepository.save(client)).thenReturn(client);

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setAddress("New York");
        serviceProvider.setId(1);
        serviceProvider.setParentClientId(1);
        when(serviceProviderRepository.save(serviceProvider)).thenReturn(serviceProvider);
        ServiceProvider result = matchingController.serviceProviderAdd(1L, serviceProvider);
        assertEquals(result, serviceProvider);
    }

    @Test
    void testConsumerAdd() {
    }

    @Test
    void sortedProvidersResponse() {
    }

    @Test
    void appointmentAdd() {
        AppointmentRepository appointmentRepository = Mockito.mock(AppointmentRepository.class);
        Model model = Mockito.mock(Model.class);
        Client client = new Client();
        client.setClientId(1);
        client.setClientName("Client1");
        String name = "Client1";
        when(clientRepository.save(client)).thenReturn(client);

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1);
        appointment.setConsumerId(1);
        appointment.setProviderID(1);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        Appointment result = matchingController.appointmentAdd(1L, appointment);
        assertEquals(result, appointment);
    }

    @Test
    void deleteCustomer() {
    }

    @Test
    void deleteServiceProviders() {
    }

    @Test
    void deleteAppointment() {
    }

    @Test
    void deleteConsumerRequest() {
    }
}