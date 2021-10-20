package backend.service;

import backend.model.RegisterModel;

import java.util.Map;

public interface RegisterService {

    Map registerManual(RegisterModel registerModel);

}
