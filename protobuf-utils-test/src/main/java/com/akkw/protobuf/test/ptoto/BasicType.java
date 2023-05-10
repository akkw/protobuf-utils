// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: basic.proto

package com.akkw.protobuf.test.ptoto;

public final class BasicType {
  private BasicType() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface BasicTypeProtoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.akkw.protobuf.test.ptoto.BasicTypeProto)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 a = 1;</code>
     * @return The a.
     */
    int getA();

    /**
     * <code>int32 b = 2;</code>
     * @return The b.
     */
    int getB();

    /**
     * <code>int32 c = 3;</code>
     * @return The c.
     */
    int getC();

    /**
     * <code>int64 d = 4;</code>
     * @return The d.
     */
    long getD();

    /**
     * <code>float e = 5;</code>
     * @return The e.
     */
    float getE();

    /**
     * <code>double f = 6;</code>
     * @return The f.
     */
    double getF();

    /**
     * <code>bool g = 7;</code>
     * @return The g.
     */
    boolean getG();

    /**
     * <code>bytes bytes = 8;</code>
     * @return The bytes.
     */
    com.google.protobuf.ByteString getBytes();
  }
  /**
   * <pre>
   *创建一个 User 对象
   * </pre>
   *
   * Protobuf type {@code com.akkw.protobuf.test.ptoto.BasicTypeProto}
   */
  public static final class BasicTypeProto extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.akkw.protobuf.test.ptoto.BasicTypeProto)
      BasicTypeProtoOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use BasicTypeProto.newBuilder() to construct.
    private BasicTypeProto(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private BasicTypeProto() {
      bytes_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new BasicTypeProto();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.akkw.protobuf.test.ptoto.BasicType.internal_static_com_akkw_protobuf_test_ptoto_BasicTypeProto_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.akkw.protobuf.test.ptoto.BasicType.internal_static_com_akkw_protobuf_test_ptoto_BasicTypeProto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto.class, com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto.Builder.class);
    }

    public static final int A_FIELD_NUMBER = 1;
    private int a_;
    /**
     * <code>int32 a = 1;</code>
     * @return The a.
     */
    @java.lang.Override
    public int getA() {
      return a_;
    }

    public static final int B_FIELD_NUMBER = 2;
    private int b_;
    /**
     * <code>int32 b = 2;</code>
     * @return The b.
     */
    @java.lang.Override
    public int getB() {
      return b_;
    }

    public static final int C_FIELD_NUMBER = 3;
    private int c_;
    /**
     * <code>int32 c = 3;</code>
     * @return The c.
     */
    @java.lang.Override
    public int getC() {
      return c_;
    }

    public static final int D_FIELD_NUMBER = 4;
    private long d_;
    /**
     * <code>int64 d = 4;</code>
     * @return The d.
     */
    @java.lang.Override
    public long getD() {
      return d_;
    }

    public static final int E_FIELD_NUMBER = 5;
    private float e_;
    /**
     * <code>float e = 5;</code>
     * @return The e.
     */
    @java.lang.Override
    public float getE() {
      return e_;
    }

    public static final int F_FIELD_NUMBER = 6;
    private double f_;
    /**
     * <code>double f = 6;</code>
     * @return The f.
     */
    @java.lang.Override
    public double getF() {
      return f_;
    }

    public static final int G_FIELD_NUMBER = 7;
    private boolean g_;
    /**
     * <code>bool g = 7;</code>
     * @return The g.
     */
    @java.lang.Override
    public boolean getG() {
      return g_;
    }

    public static final int BYTES_FIELD_NUMBER = 8;
    private com.google.protobuf.ByteString bytes_;
    /**
     * <code>bytes bytes = 8;</code>
     * @return The bytes.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getBytes() {
      return bytes_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (a_ != 0) {
        output.writeInt32(1, a_);
      }
      if (b_ != 0) {
        output.writeInt32(2, b_);
      }
      if (c_ != 0) {
        output.writeInt32(3, c_);
      }
      if (d_ != 0L) {
        output.writeInt64(4, d_);
      }
      if (e_ != 0F) {
        output.writeFloat(5, e_);
      }
      if (f_ != 0D) {
        output.writeDouble(6, f_);
      }
      if (g_ != false) {
        output.writeBool(7, g_);
      }
      if (!bytes_.isEmpty()) {
        output.writeBytes(8, bytes_);
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (a_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, a_);
      }
      if (b_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, b_);
      }
      if (c_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, c_);
      }
      if (d_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(4, d_);
      }
      if (e_ != 0F) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(5, e_);
      }
      if (f_ != 0D) {
        size += com.google.protobuf.CodedOutputStream
          .computeDoubleSize(6, f_);
      }
      if (g_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(7, g_);
      }
      if (!bytes_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(8, bytes_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto)) {
        return super.equals(obj);
      }
      com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto other = (com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto) obj;

      if (getA()
          != other.getA()) return false;
      if (getB()
          != other.getB()) return false;
      if (getC()
          != other.getC()) return false;
      if (getD()
          != other.getD()) return false;
      if (java.lang.Float.floatToIntBits(getE())
          != java.lang.Float.floatToIntBits(
              other.getE())) return false;
      if (java.lang.Double.doubleToLongBits(getF())
          != java.lang.Double.doubleToLongBits(
              other.getF())) return false;
      if (getG()
          != other.getG()) return false;
      if (!getBytes()
          .equals(other.getBytes())) return false;
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + A_FIELD_NUMBER;
      hash = (53 * hash) + getA();
      hash = (37 * hash) + B_FIELD_NUMBER;
      hash = (53 * hash) + getB();
      hash = (37 * hash) + C_FIELD_NUMBER;
      hash = (53 * hash) + getC();
      hash = (37 * hash) + D_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getD());
      hash = (37 * hash) + E_FIELD_NUMBER;
      hash = (53 * hash) + java.lang.Float.floatToIntBits(
          getE());
      hash = (37 * hash) + F_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          java.lang.Double.doubleToLongBits(getF()));
      hash = (37 * hash) + G_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getG());
      hash = (37 * hash) + BYTES_FIELD_NUMBER;
      hash = (53 * hash) + getBytes().hashCode();
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     *创建一个 User 对象
     * </pre>
     *
     * Protobuf type {@code com.akkw.protobuf.test.ptoto.BasicTypeProto}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.akkw.protobuf.test.ptoto.BasicTypeProto)
        com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProtoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.akkw.protobuf.test.ptoto.BasicType.internal_static_com_akkw_protobuf_test_ptoto_BasicTypeProto_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.akkw.protobuf.test.ptoto.BasicType.internal_static_com_akkw_protobuf_test_ptoto_BasicTypeProto_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto.class, com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto.Builder.class);
      }

      // Construct using com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        a_ = 0;

        b_ = 0;

        c_ = 0;

        d_ = 0L;

        e_ = 0F;

        f_ = 0D;

        g_ = false;

        bytes_ = com.google.protobuf.ByteString.EMPTY;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.akkw.protobuf.test.ptoto.BasicType.internal_static_com_akkw_protobuf_test_ptoto_BasicTypeProto_descriptor;
      }

      @java.lang.Override
      public com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto getDefaultInstanceForType() {
        return com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto.getDefaultInstance();
      }

      @java.lang.Override
      public com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto build() {
        com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto buildPartial() {
        com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto result = new com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto(this);
        result.a_ = a_;
        result.b_ = b_;
        result.c_ = c_;
        result.d_ = d_;
        result.e_ = e_;
        result.f_ = f_;
        result.g_ = g_;
        result.bytes_ = bytes_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto) {
          return mergeFrom((com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto other) {
        if (other == com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto.getDefaultInstance()) return this;
        if (other.getA() != 0) {
          setA(other.getA());
        }
        if (other.getB() != 0) {
          setB(other.getB());
        }
        if (other.getC() != 0) {
          setC(other.getC());
        }
        if (other.getD() != 0L) {
          setD(other.getD());
        }
        if (other.getE() != 0F) {
          setE(other.getE());
        }
        if (other.getF() != 0D) {
          setF(other.getF());
        }
        if (other.getG() != false) {
          setG(other.getG());
        }
        if (other.getBytes() != com.google.protobuf.ByteString.EMPTY) {
          setBytes(other.getBytes());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new java.lang.NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 8: {
                a_ = input.readInt32();

                break;
              } // case 8
              case 16: {
                b_ = input.readInt32();

                break;
              } // case 16
              case 24: {
                c_ = input.readInt32();

                break;
              } // case 24
              case 32: {
                d_ = input.readInt64();

                break;
              } // case 32
              case 45: {
                e_ = input.readFloat();

                break;
              } // case 45
              case 49: {
                f_ = input.readDouble();

                break;
              } // case 49
              case 56: {
                g_ = input.readBool();

                break;
              } // case 56
              case 66: {
                bytes_ = input.readBytes();

                break;
              } // case 66
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }

      private int a_ ;
      /**
       * <code>int32 a = 1;</code>
       * @return The a.
       */
      @java.lang.Override
      public int getA() {
        return a_;
      }
      /**
       * <code>int32 a = 1;</code>
       * @param value The a to set.
       * @return This builder for chaining.
       */
      public Builder setA(int value) {
        
        a_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 a = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearA() {
        
        a_ = 0;
        onChanged();
        return this;
      }

      private int b_ ;
      /**
       * <code>int32 b = 2;</code>
       * @return The b.
       */
      @java.lang.Override
      public int getB() {
        return b_;
      }
      /**
       * <code>int32 b = 2;</code>
       * @param value The b to set.
       * @return This builder for chaining.
       */
      public Builder setB(int value) {
        
        b_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 b = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearB() {
        
        b_ = 0;
        onChanged();
        return this;
      }

      private int c_ ;
      /**
       * <code>int32 c = 3;</code>
       * @return The c.
       */
      @java.lang.Override
      public int getC() {
        return c_;
      }
      /**
       * <code>int32 c = 3;</code>
       * @param value The c to set.
       * @return This builder for chaining.
       */
      public Builder setC(int value) {
        
        c_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 c = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearC() {
        
        c_ = 0;
        onChanged();
        return this;
      }

      private long d_ ;
      /**
       * <code>int64 d = 4;</code>
       * @return The d.
       */
      @java.lang.Override
      public long getD() {
        return d_;
      }
      /**
       * <code>int64 d = 4;</code>
       * @param value The d to set.
       * @return This builder for chaining.
       */
      public Builder setD(long value) {
        
        d_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 d = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearD() {
        
        d_ = 0L;
        onChanged();
        return this;
      }

      private float e_ ;
      /**
       * <code>float e = 5;</code>
       * @return The e.
       */
      @java.lang.Override
      public float getE() {
        return e_;
      }
      /**
       * <code>float e = 5;</code>
       * @param value The e to set.
       * @return This builder for chaining.
       */
      public Builder setE(float value) {
        
        e_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>float e = 5;</code>
       * @return This builder for chaining.
       */
      public Builder clearE() {
        
        e_ = 0F;
        onChanged();
        return this;
      }

      private double f_ ;
      /**
       * <code>double f = 6;</code>
       * @return The f.
       */
      @java.lang.Override
      public double getF() {
        return f_;
      }
      /**
       * <code>double f = 6;</code>
       * @param value The f to set.
       * @return This builder for chaining.
       */
      public Builder setF(double value) {
        
        f_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>double f = 6;</code>
       * @return This builder for chaining.
       */
      public Builder clearF() {
        
        f_ = 0D;
        onChanged();
        return this;
      }

      private boolean g_ ;
      /**
       * <code>bool g = 7;</code>
       * @return The g.
       */
      @java.lang.Override
      public boolean getG() {
        return g_;
      }
      /**
       * <code>bool g = 7;</code>
       * @param value The g to set.
       * @return This builder for chaining.
       */
      public Builder setG(boolean value) {
        
        g_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool g = 7;</code>
       * @return This builder for chaining.
       */
      public Builder clearG() {
        
        g_ = false;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString bytes_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>bytes bytes = 8;</code>
       * @return The bytes.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString getBytes() {
        return bytes_;
      }
      /**
       * <code>bytes bytes = 8;</code>
       * @param value The bytes to set.
       * @return This builder for chaining.
       */
      public Builder setBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        bytes_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bytes bytes = 8;</code>
       * @return This builder for chaining.
       */
      public Builder clearBytes() {
        
        bytes_ = getDefaultInstance().getBytes();
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:com.akkw.protobuf.test.ptoto.BasicTypeProto)
    }

    // @@protoc_insertion_point(class_scope:com.akkw.protobuf.test.ptoto.BasicTypeProto)
    private static final com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto();
    }

    public static com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<BasicTypeProto>
        PARSER = new com.google.protobuf.AbstractParser<BasicTypeProto>() {
      @java.lang.Override
      public BasicTypeProto parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<BasicTypeProto> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<BasicTypeProto> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.akkw.protobuf.test.ptoto.BasicType.BasicTypeProto getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_akkw_protobuf_test_ptoto_BasicTypeProto_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_akkw_protobuf_test_ptoto_BasicTypeProto_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013basic.proto\022\034com.akkw.protobuf.test.pt" +
      "oto\"l\n\016BasicTypeProto\022\t\n\001a\030\001 \001(\005\022\t\n\001b\030\002 " +
      "\001(\005\022\t\n\001c\030\003 \001(\005\022\t\n\001d\030\004 \001(\003\022\t\n\001e\030\005 \001(\002\022\t\n\001" +
      "f\030\006 \001(\001\022\t\n\001g\030\007 \001(\010\022\r\n\005bytes\030\010 \001(\014B)\n\034com" +
      ".akkw.protobuf.test.ptotoB\tBasicTypeb\006pr" +
      "oto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_akkw_protobuf_test_ptoto_BasicTypeProto_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_akkw_protobuf_test_ptoto_BasicTypeProto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_akkw_protobuf_test_ptoto_BasicTypeProto_descriptor,
        new java.lang.String[] { "A", "B", "C", "D", "E", "F", "G", "Bytes", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}