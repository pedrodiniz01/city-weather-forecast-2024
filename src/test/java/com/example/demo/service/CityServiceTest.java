package com.example.demo.service;

import com.example.demo.client.OpenWeatherApiRemoteService;
import com.example.demo.data.City;
import com.example.demo.dtos.response.CityInfoDto;
import com.example.demo.exceptions.CityAlreadyRegisteredException;
import com.example.demo.exceptions.CityNotFoundException;
import com.example.demo.exceptions.InvalidApiResponseException;
import com.example.demo.mapper.CityMapper;
import com.example.demo.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityMapper cityMapper;

    @Mock
    private OpenWeatherApiRemoteService openWeatherApiRemoteService;

    @InjectMocks
    private CityService cityService;

    private CityInfoDto cityInfoDto;

    @BeforeEach
    void setUp() {
        cityInfoDto = CityInfoDto.builder()
                .name("Porto")
                .country("PT")
                .lat(0.0)
                .lon(0.0)
                .build();
    }

    @Test
    void testRegisterCity_WhenCityAlreadyExists_Error() {
        // Arrange
        when(cityRepository.existsByName(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(CityAlreadyRegisteredException.class, () -> cityService.registerCity(anyString()));
    }
    @Test
    void testRegisterCity_WithNonExistentCity_Error() {
        // Arrange
        when(cityRepository.existsByName(anyString())).thenReturn(false);
        when(openWeatherApiRemoteService.getCityInfo(anyString(), eq("Porto")))
                .thenThrow(new InvalidApiResponseException("API", "Reason", "Info"));

        // Act && Assert
        InvalidApiResponseException exception = assertThrows(InvalidApiResponseException.class,
                () -> cityService.registerCity("Porto"));
        assertEquals("API", exception.getApi());
        assertEquals("Reason", exception.getReason());
        assertEquals("Info", exception.getAdditionalInfo());
    }

    @Test
    public void testRegisterCity_Success() {
        // Arrange
        when(cityRepository.existsByName(anyString())).thenReturn(false);
        when(openWeatherApiRemoteService.getCityInfo(anyString(), anyString())).thenReturn(Collections.singletonList(cityInfoDto));

        // Act
         cityService.registerCity(anyString());

        // Assert
        verify(cityRepository).save(any(City.class));
    }
    @Test
    void testGetCityForecast_CityNotFound_Error() {
        // Arrange
        String cityName = "NonExistentCity";
        when(cityRepository.findByName(eq(cityName))).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(CityNotFoundException.class, () -> cityService.getCityForecast(cityName, 5));

        // Verify
        verify(cityRepository).findByName(eq(cityName));
        verifyNoInteractions(openWeatherApiRemoteService, cityMapper);
    }
}