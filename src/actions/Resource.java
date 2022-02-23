package src.actions;

public class Resource {
    private final byte[] resource;
    private long resourceSize;
    private String resourceType;

    public Resource(byte[] resource){
        this.resource = resource;
    }

    public Resource(byte[] resource, String resourceType){
        this.resource = resource;
        this.resourceType = resourceType;
        this.resourceSize = resource.length;
    }

    public Resource(byte[] resource, long resourceSize, String resourceType){
        this.resource = resource;
        this.resourceSize = resourceSize;
        this.resourceType = resourceType;
    }

    public byte[] getResource() {
        return resource;
    }

    public long getResourceSize() {
        return resourceSize;
    }

    public String getResourceType() {
        return resourceType;
    }
}
