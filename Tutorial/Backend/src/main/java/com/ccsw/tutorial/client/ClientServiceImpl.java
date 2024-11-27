package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lucía Sánchez
 *
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Client get(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findAll() {
        return (List<Client>) this.clientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, ClientDto dto) {
        Client client;
        if (id == null && clientRepository.findByName(dto.getName()).isEmpty()) {
            client = new Client();
            client.setName(dto.getName());
            this.clientRepository.save(client);
        } else if (id != null) {
            client = this.clientRepository.findById(id).orElse(null);
            client.setName(dto.getName());
            this.clientRepository.save(client);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Long id) throws Exception {
        if (this.clientRepository.findById(id).orElse(null) == null) {
            throw new Exception("The client with id " + id + "doesn't exist");
        }

        this.clientRepository.deleteById(id);
    }
}
