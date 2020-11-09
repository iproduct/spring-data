package course.springdata.mapping.service.impl;

import course.springdata.mapping.dao.AddressRepository;
import course.springdata.mapping.entity.Address;
import course.springdata.mapping.exception.NonexistingEntityException;
import course.springdata.mapping.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepo;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public List<Address> getAllAddresss() {
        return addressRepo.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        return addressRepo.findById(id).orElseThrow(()->
                new NonexistingEntityException(String.format("Address with ID=%s does not exist.%n")));
    }

    @Transactional
    @Override
    public Address addAddress(Address address) {
        address.setId(null);
        return addressRepo.save(address);
    }

    @Transactional
    @Override
    public Address updateAddress(Address address) {
        Address old = getAddressById(address.getId());
        return addressRepo.save(address);
    }

    @Transactional
    @Override
    public Address deleteAddress(Long id) {
        Address removed = getAddressById(id);
        addressRepo.deleteById(id);
        return removed;
    }

    @Override
    public long getAddressCount() {
        return addressRepo.count();
    }
}
