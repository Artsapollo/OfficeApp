package springData.service;
import dataBase.entity.Office;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import springData.repository.OfficesRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OfficesServiceIntegrationTest {
    @Spy
    @InjectMocks
    private OfficesService officesService = new OfficesServiceImpl();
    @Mock
    private OfficesRepository officesRepository;

    private Office office1 = new Office();
    private Office office2 = new Office();

    @Test
    public void testFindByTargetBetween() {
        Set<Office> office = Stream.of(office1, office2).collect(Collectors.toSet());
        doReturn(office).when(officesRepository).findByTargetBetween(any(), any());
        Set<Office> result = officesService.findByTargetBetween(0, 0);
        assertTrue(office.containsAll(result) && result.containsAll(office));

    }

    @Test
    public void testGetAllOffices() {
        List<Office> office = Arrays.asList(new Office[] { office1, office2 });
        doReturn(office).when(officesRepository).findAll();
        Set<Office> result = officesService.getAllOffices();
        assertTrue(office.containsAll(result) && result.containsAll(office));
    }


    @Test
    public void testFindOfficeById(){
        doReturn(Optional.of(office1)).when(officesRepository).findById(java.math.BigDecimal.valueOf(11));
        Office result = officesService.findOfficeById(java.math.BigDecimal.valueOf(11));
        assertEquals(office1, result);
    }

    @Test
    public void testInsertOffice() {
        doReturn(office1).when(officesRepository).save(any());
        officesService.insertOffice(office1);
        verify(officesRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateOffice() {
        doReturn(office1).when(officesRepository).save(any());
        officesService.updateOffice(office1);
        verify(officesRepository, times(1)).save(any());
    }

    @Test
    public void testDeleteOffice() {
        doNothing().when(officesRepository).deleteById(any());
        officesService.deleteOffice(office1.getOffice());
        verify(officesRepository, times(1)).deleteById(any());
    }
}
