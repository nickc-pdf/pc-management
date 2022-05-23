package ch.bzz.pcmanagement.data;

import ch.bzz.pcmanagement.model.Component;
import ch.bzz.pcmanagement.model.Manufacturer;
import ch.bzz.pcmanagement.model.PC;
import ch.bzz.pcmanagement.service.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static DataHandler instance = null;
    private List<Component> componentList;
    private List<Manufacturer> manufacturerList;
    private List<PC> pcList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setPcList(new ArrayList<>());
        readPcJSON();
        setManufacturerList(new ArrayList<>());
        readManufacturerJSON();
        setComponentList(new ArrayList<>());
        readComponentJSON();
    }

    /**
     * gets the only instance of this class
     * @return
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }


    /**
     * reads all components
     * @return list of components
     */
    public List<Component> readAllComponents() {
        return getComponentList();
    }

    /**
     * reads a component by its id
     * @param componentID
     * @return the Component (null=not found)
     */
    public Component readComponentID(int componentID) {
        Component component = null;
        for (Component entry : getComponentList()) {
            if (entry.getId() == componentID) {
                component = entry;
            }
        }
        return component;
    }

    /**
     * reads all Manufacturers
     * @return list of Manufacturers
     */
    public List<Manufacturer> readAllManufacturer() {
        return getManufacturerList();
    }

    /**
     * reads a Manufacturer by its id
     * @param manufacturerID
     * @return the Manufacturer (null=not found)
     */
    public Manufacturer readManufacturerID(int manufacturerID) {
        Manufacturer manufacturer = null;
        for (Manufacturer entry : getManufacturerList()) {
            if (entry.getId() == manufacturerID) {
                manufacturer = entry;
            }
        }
        return manufacturer;
    }

    /**
     * reads all PCs
     * @return list of PCs
     */
    public List<PC> readAllPc() {
        return getPCList();
    }

    /**
     * reads a PC by its id
     * @param pcID
     * @return the PC (null=not found)
     */
    public PC readPCID(int pcID) {
        PC pc = null;
        for (PC entry : getPCList()) {
            if (entry.getId() == pcID) {
                pc = entry;
            }
        }
        return pc;
    }
    /**
     * reads the components from the JSON-file
     */
    private void readComponentJSON() {
        try {
            String path = Config.getProperty("componentJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Component[] components = objectMapper.readValue(jsonData, Component[].class);
            for (Component component : components) {
                getComponentList().add(component);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the manufactures from the JSON-file
     */
    private void readManufacturerJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("manufacturerJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Manufacturer[] manufacturers = objectMapper.readValue(jsonData, Manufacturer[].class);
            for (Manufacturer manufacturer : manufacturers) {
                getManufacturerList().add(manufacturer);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the components from the JSON-file
     */
    private void readPcJSON() {
        try {
            String path = Config.getProperty("pcJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            PC[] pcs = objectMapper.readValue(jsonData, PC[].class);
            for (PC pc : pcs) {
               getPCList().add(pc);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * gets componentList
     *
     * @return value of componentList
     */
    private List<Component> getComponentList() {
        return componentList;
    }

    /**
     * sets componentList
     *
     * @param componentList the value to set
     */
    private void setComponentList(List<Component> componentList) {
        this.componentList = componentList;
    }

    /**
     * gets manufacturerList
     *
     * @return value of manufacturerList
     */
    private List<Manufacturer> getManufacturerList() {
        return manufacturerList;
    }

    /**
     * sets manufacturerList
     *
     * @param manufacturerList the value to set
     */
    private void setManufacturerList(List<Manufacturer> manufacturerList) {
        this.manufacturerList = manufacturerList;
    }

    /**
     * gets pcList
     *
     * @return value of pcList
     */
    private List<PC> getPCList() {
        return pcList;
    }

    /**
     * sets pcList
     *
     * @param pcList the value to set
     */
    private void setPcList(List<PC> pcList) {
        this.pcList = pcList;
    }


}