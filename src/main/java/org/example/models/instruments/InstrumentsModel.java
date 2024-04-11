package org.example.models.instruments;

import java.util.List;

public interface InstrumentsModel {

   List<? extends InstrumentModel> getInstruments();
}
