package com.ftn.sbnz.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.models.BiljnaKultura;
import com.ftn.sbnz.service.dto.request.LoginCredentials;
import com.ftn.sbnz.service.dto.request.ParcelDto;
import com.ftn.sbnz.service.dto.response.ParcelResponseDto;
import com.ftn.sbnz.service.dto.response.TokenResponse;
import com.ftn.sbnz.service.exception.BadRequestException;
import com.ftn.sbnz.service.model.User;
import com.ftn.sbnz.service.security.TokenUtils;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class SampleAppController {
	private final SampleAppService sampleService;
	private final AuthenticationManager authenticationManager;
	private final TokenUtils tokenUtils;

	@RequestMapping(
		value = "/login",
		method = RequestMethod.POST
    )
    public ResponseEntity<TokenResponse> login(@RequestBody LoginCredentials authenticationRequest) {
        try{
            Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User)authentication.getPrincipal();
            String jwt = this.tokenUtils.generateToken(user);
			TokenResponse response = new TokenResponse(jwt);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (AuthenticationException exception){
            throw new BadRequestException("Wrong email or password");
        }
    }

	@RequestMapping(value = "/parcel/{id}", method = RequestMethod.GET)
	public ResponseEntity<ParcelResponseDto> getParcel(@PathVariable(value = "id") Long parcelId){
		return ResponseEntity.status(HttpStatus.OK).body(this.sampleService.getParcel(parcelId));
	}

	@RequestMapping(value = "/parcel", method = RequestMethod.POST)
	public ResponseEntity<ParcelResponseDto> createParcel(@RequestBody ParcelDto parcelDto){
		return ResponseEntity.status(HttpStatus.OK).body(this.sampleService.createParcel(parcelDto));
	}

	@RequestMapping(value = "/parcels", method = RequestMethod.GET)
	public ResponseEntity<List<ParcelResponseDto>> getParcels(){
		return ResponseEntity.status(HttpStatus.OK).body(this.sampleService.getParcels());
	}

	@RequestMapping(value = "/parcel/{parcelId}/sow/{plant}", method = RequestMethod.POST)
	public ResponseEntity<ParcelResponseDto> plant(@PathVariable(value = "parcelId") Long parcelId, @PathVariable(value = "plant") BiljnaKultura plant){
		return ResponseEntity.status(HttpStatus.OK).body(this.sampleService.plant(parcelId, plant));
	}
	
}
