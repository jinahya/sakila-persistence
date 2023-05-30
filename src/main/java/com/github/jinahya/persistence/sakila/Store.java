package com.github.jinahya.persistence.sakila;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Optional;

/**
 * An entity class for mapping {@value #TABLE_NAME} table.
 * <p>
 * <blockquote>
 * The {@value #TABLE_NAME} table lists all stores in the system. All inventory is assigned to specific stores, and
 * staff and customers are assigned a “home store”.<br/>The {@value TABLE_NAME} table refers to the
 * {@value Staff#TABLE_NAME} and {@value Address#TABLE_NAME} tables using foreign keys and is referred to by the
 * {@value Staff#TABLE_NAME}, {@value Customer#TABLE_NAME}, and {@value Inventory#TABLE_NAME} tables.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-store.html">5.1.16 The store Table</a>
 */
@Entity
@Table(name = Store.TABLE_NAME)
public class Store
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "store";

    /**
     * The name of the database column to which the {@link Store_#storeId storeId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_STORE_ID = "store_id";

    static final int COLUMN_VALUE_STORE_ID_THE = 3;

    /**
     * The name of the database column to which the {@link Store_#managerStaffId managerStaffId} attribute maps. The
     * value is {@value}.
     */
    public static final String COLUMN_NAME_MANAGER_STAFF_ID = "manager_staff_id";

    /**
     * The name of the database column to which the {@link Store_#addressId addressId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_ADDRESS_ID = Address.COLUMN_NAME_ADDRESS_ID;

    /**
     * Creates a new instance with specified value of {@link Store_#storeId storeId} attribute.
     *
     * @param storeId the value of the {@link Store_#storeId storeId} attribute.
     * @return a new instance.
     */
    public static Store ofStoreId(final Integer storeId) {
        final var instance = new Store();
        instance.storeId = storeId;
        return instance;
    }

    /**
     * Creates a new instance.
     */
    public Store() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "storeId=" + storeId +
               ",managerStaffId=" + managerStaffId +
               ",addressId=" + addressId +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Store)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    Integer identifier() {
        return getStoreId();
    }

    /**
     * Returns current value of {@link Store_#storeId storeId} attribute.
     *
     * @return current value of the {@link Store_#storeId storeId} attribute.
     */
    public Integer getStoreId() {
        return storeId;
    }

    @PostConstruct
    protected void onPostConstruct() {
        if (managerStaff != null) {
            managerStaff.setStore(this);
        }
    }

    /**
     * Replaces current value of {@link Store_#storeId storeId} attribute with specified value.
     *
     * @param storeId new value for the {@link Store_#storeId storeId} attribute.
     * @deprecated for removal
     */
    @Deprecated
    private void setStoreId(final Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * Returns current value of {@link Store_#managerStaffId managerStaffId} attribute.
     *
     * @return current value of the {@link Store_#managerStaffId managerStaffId} attribute.
     */
    public Integer getManagerStaffId() {
        return managerStaffId;
    }

    /**
     * Replaces current value of {@link Store_#managerStaffId managerStaffId} attribute with specified value.
     *
     * @param managerStaffId new value for the {@link Store_#managerStaffId managerStaffId} attribute.
     */
    protected void setManagerStaffId(final Integer managerStaffId) {
        this.managerStaffId = managerStaffId;
    }

    /**
     * Returns current value of {@link Store_#addressId addressId} attribute.
     *
     * @return current value of the {@link Store_#addressId addressId} attribute.
     */
    public Integer getAddressId() {
        return addressId;
    }

    /**
     * Replaces current value of {@link Store_#addressId addressId} attribute with specified value.
     *
     * @param addressId new value for the {@link Store_#addressId addressId} attribute.
     */
    protected void setAddressId(final Integer addressId) {
        this.addressId = addressId;
    }

    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_STORE_ID, nullable = false,
            insertable = true, // EclipseLink
            updatable = false)
    private Integer storeId;

    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_MANAGER_STAFF_ID, nullable = false)
    private Integer managerStaffId;

    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ADDRESS_ID, nullable = false)
    private Integer addressId;

    /**
     * Returns current value of {@link Store_#managerStaff managerStaff} attribute.
     *
     * @return current value of the {@link Store_#managerStaff managerStaff} attribute.
     */
    public Staff getManagerStaff() {
        return managerStaff;
    }

    /**
     * Replaces current value of {@link Store_#managerStaff managerStaff} attribute with specified value.
     *
     * @param managerStaff new value for the {@link Store_#managerStaff managerStaff} attribute.
     * @apiNote This method updates {@link Store_#managerStaffId managerStaffId} attribute with the value of
     * {@code managerStaff?.staffId}.
     */
    public void setManagerStaff(final Staff managerStaff) {
        this.managerStaff = managerStaff;
        setManagerStaffId(
                Optional.ofNullable(this.managerStaff)
                        .map(Staff::getStaffId)
                        .orElse(null)
        );
        /*
         * 1. managerStaff 의 store 가 이미 이 store 이어야 하는가?
         * 2. managerStaff 의 store 를 이 store 로 갱신만 하면 되는가?
         */
        if (this.managerStaff != null) {
            this.managerStaff.setStore(this);
        }
    }

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_MANAGER_STAFF_ID, nullable = false, insertable = false, updatable = false)
    private Staff managerStaff;

    /**
     * Returns current value of {@link Store_#address address} attribute.
     *
     * @return current value of the {@link Store_#address address} attribute.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Replaces current value of {@link Store_#address address} attribute with specified value.
     *
     * @param address new value for the {@link Store_#address address} attribute.
     * @apiNote This method also updates {@link Store_#addressId addressId} attribute with {@code address?.addressId}.
     */
    public void setAddress(final Address address) {
        this.address = address;
        setAddressId(
                Optional.ofNullable(this.address)
                        .map(Address::getAddressId)
                        .orElse(null)
        );
    }

    @NotNull
    @Valid
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_ADDRESS_ID, nullable = false, insertable = false, updatable = false)
    private Address address;

//    /**
//     * Returns staffs of this store.
//     *
//     * @return a list of staff of this store.
//     */
//    public List<Staff> getStaffs() {
//        if (staffs == null) {
//            staffs = new ArrayList<>();
//        }
//        return staffs;
//    }
//
//    /**
//     * Staffs of this store.
//     */
//    @OneToMany(mappedBy = "store", orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
//    private List<@Valid @NotNull Staff> staffs;
}
