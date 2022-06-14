package ch.bzz.pcmanagement.data;

import ch.bzz.pcmanagement.model.Component;
import ch.bzz.pcmanagement.model.Manufacturer;
import ch.bzz.pcmanagement.model.PC;
import ch.bzz.pcmanagement.service.Config;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public final class DataHandler {
    private static List<Component> componentList;
    private static List<Manufacturer> manufacturerList;
    private static List<PC> pcList;
    private static int manufacturerId;
    private static int componentId;
    private static int pcId;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
    }


    /**
     * reads all components
     * @return list of components
     */
    public static List<Component> readAllComponents() {
        return getComponentList();
    }

    /**
     * reads a component by its id
     * @param componentID
     * @return the Component (null=not found)
     */
    public static Component readComponentID(int componentID) {
        Component component = null;
        for (Component entry : getComponentList()) {
            if (entry.getId() == componentID) {
                component = entry;
            }
        }
        return component;
    }

    /**
     * inserts a new component into the componentList
     *
     * @param component the book to be saved
     */
    public static void insertComponent(Component component) {
        getComponentList().add(component);
        writeComponentJSON();
    }

    /**
     * updates the componentList
     */
    public static void updateComponent() {
        writeComponentJSON();
    }

    /**
     * deletes a component identified by the componentID
     * @param componentID  the key
     * @return  success=true/false
     */
    public static boolean deleteComponent(int componentID) {
        Component component = readComponentID(componentID);
        if (component != null) {
            getComponentList().remove(component);
            writeComponentJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads all Manufacturers
     * @return list of Manufacturers
     */
    public static List<Manufacturer> readAllManufacturer() {
        return getManufacturerList();
    }

    /**
     * reads a Manufacturer by its id
     * @param manufacturerID
     * @return the Manufacturer (null=not found)
     */
    public static Manufacturer readManufacturerID(int manufacturerID) {
        Manufacturer manufacturer = null;
        for (Manufacturer entry : getManufacturerList()) {
            if (entry.getId() == manufacturerID) {
                manufacturer = entry;
            }
        }
        return manufacturer;
    }

    /**
     * inserts a new manufacturer into the manufacturerList
     *
     * @param manufacturer the book to be saved
     */
    public static void insertManufacturer(Manufacturer manufacturer) {
        getManufacturerList().add(manufacturer);
        writeManufacturerJSON();
    }

    /**
     * updates the manufacturertList
     */
    public static void updateManufacturer() {
        writeManufacturerJSON();
    }

    /**
     * deletes a manufacturer identified by the manufacturerID
     * @param manufacturerID  the key
     * @return  success=true/false
     */
    public static boolean deleteManufacturer(int manufacturerID) {
        Manufacturer manufacturer = readManufacturerID(manufacturerID);
        if (manufacturer != null) {
            getManufacturerList().remove(manufacturerID);
            writeManufacturerJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads all PCs
     * @return list of PCs
     */
    public static List<PC> readAllPc() {
        return getPCList();
    }

    /**
     * reads a PC by its id
     * @param pcID
     * @return the PC (null=not found)
     */
    public static PC readPCID(int pcID) {
        PC pc = null;
        for (PC entry : getPCList()) {
            if (entry.getId() == pcID) {
                pc = entry;
            }
        }
        return pc;
    }

    /**
     * inserts a new pc into the pcList
     *
     * @param pc the book to be saved
     */
    public static void insertPC(PC pc) {
        getPCList().add(pc);
        writePCJSON();
    }

    /**
     * updates the pcList
     */
    public static void updatePC() {
        writePCJSON();
    }

    /**
     * deletes a pc identified by the pcID
     * @param pcID  the key
     * @return  success=true/false
     */
    public static boolean deletePC(int pcID) {
        PC pc = readPCID(pcID);
        if (pc != null) {
            getPCList().remove(pcID);
            writePCJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * writes the componentList to the JSON-file
     */
    private static void writeComponentJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String componentPath = Config.getProperty("componentJSON");
        try {
            fileOutputStream = new FileOutputStream(componentPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getComponentList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the components from the JSON-file
     */
    private static void readComponentJSON() {
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
     * writes the manufacturerList to the JSON-file
     */
    private static void writeManufacturerJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String manufacturerPath = Config.getProperty("manufacturerJSON");
        try {
            fileOutputStream = new FileOutputStream(manufacturerPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getManufacturerList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the manufactures from the JSON-file
     */
    private static void readManufacturerJSON() {
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
     * writes the pcList to the JSON-file
     */
    private static void writePCJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String pcPath = Config.getProperty("pcJSON");
        try {
            fileOutputStream = new FileOutputStream(pcPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getComponentList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the components from the JSON-file
     */
    private static void readPcJSON() {
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
    private static List<Component> getComponentList() {
        if (componentList == null) {
            setComponentList(new ArrayList<>());
            readComponentJSON();
        }

        return componentList;
    }

    /**
     * sets componentList
     *
     * @param componentList the value to set
     */
    private static void setComponentList(List<Component> componentList) {
        DataHandler.componentList = componentList;
    }

    /**
     * gets manufacturerList
     *
     * @return value of manufacturerList
     */
    private static List<Manufacturer> getManufacturerList() {
        if (manufacturerList == null) {
            setManufacturerList(new ArrayList<>());
            readManufacturerJSON();
        }

        return manufacturerList;
    }

    /**
     * sets manufacturerList
     *
     * @param manufacturerList the value to set
     */
    private static void setManufacturerList(List<Manufacturer> manufacturerList) {
        DataHandler.manufacturerList = manufacturerList;
    }

    /**
     * gets pcList
     *
     * @return value of pcList
     */
    private static List<PC> getPCList() {
        if (pcList == null) {
            setPcList(new ArrayList<>());
            readPcJSON();
        }

        return pcList;
    }

    /**
     * sets pcList
     *
     * @param pcList the value to set
     */
    private static void setPcList(List<PC> pcList) {
        DataHandler.pcList = pcList;
    }

    /**
     * return an id that isn't used already
     * @return
     */
    public static int getManufacturerId() {
        while (readManufacturerID(manufacturerId) != null) {
            manufacturerId++;
        }
        return manufacturerId;
    }

    /**
     * return an id that isn't used already
     * @return
     */
    public static int getComponentId() {
        while (readComponentID(componentId) != null) {
            componentId++;
        }
        return componentId;
    }

    /**
     * return an id that isn't used already
     * @return
     */
    public static int getPCId() {
        while (readPCID(pcId) != null) {
            pcId++;
        }
        return pcId;
    }


}